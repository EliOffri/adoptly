package com.example.dogadoption.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.dogadoption.R
import com.example.dogadoption.data.remote.model.DogBreed
import com.example.dogadoption.databinding.FragmentHomeBinding
import com.example.dogadoption.util.Resource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: DogBreedAdapter

    private var fullList: List<DogBreed> = emptyList()
    private var currentQuery = ""
    private var currentFeaturedBreed: DogBreed? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
        observeViewModel()
        binding.fabAddDog.setOnClickListener { showAddDogBottomSheet() }
        binding.btnFeaturedFavorite.setOnClickListener {
            currentFeaturedBreed?.let { viewModel.toggleFavorite(it) }
        }
        viewModel.isFeaturedFavorite.observe(viewLifecycleOwner) { isFav ->
            binding.btnFeaturedFavorite.setImageResource(
                if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = DogBreedAdapter(
            onBreedClicked = { breed -> navigateToDetail(breed) },
            onBreedLongClicked = { breed -> showDeleteDogDialog(breed) }
        )
        binding.recyclerViewBreeds.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewBreeds.adapter = adapter
    }

    private fun setupSearch() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                currentQuery = s?.toString()?.trim() ?: ""
                applyFilters()
            }
        })
    }

    private fun applyFilters() {
        val filtered = fullList.filter { matchesQuery(it, currentQuery) }
        adapter.submitList(filtered)
        if (filtered.isNotEmpty()) {
            updateFeaturedCard(filtered.first())
        }
    }

    private fun matchesQuery(breed: DogBreed, query: String): Boolean {
        if (query.isEmpty()) return true
        return breed.name.contains(query, ignoreCase = true) ||
            breed.subBreeds.any { it.contains(query, ignoreCase = true) }
    }

    private fun observeViewModel() {
        viewModel.combinedList.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewBreeds.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewBreeds.visibility = View.VISIBLE
                    fullList = resource.data ?: emptyList()
                    applyFilters()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewBreeds.visibility = View.VISIBLE
                    Snackbar.make(binding.root, getString(R.string.error_loading_data), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.retry)) { viewModel.loadBreeds() }
                        .show()
                }
            }
        }
    }

    private fun updateFeaturedCard(breed: DogBreed) {
        currentFeaturedBreed = breed
        viewModel.setFeaturedBreed(breed.name)
        binding.textFeaturedName.text = breed.name.replaceFirstChar { it.uppercase() }
        binding.textFeaturedMeta.text = if (breed.subBreeds.isEmpty()) {
            getString(R.string.no_sub_breeds)
        } else {
            getString(R.string.sub_breeds_count, breed.subBreeds.size)
        }
        Glide.with(this)
            .load(breed.imageUrl.ifBlank { null })
            .placeholder(R.drawable.ic_placeholder_dog)
            .error(R.drawable.ic_placeholder_dog)
            .centerCrop()
            .into(binding.imageFeatured)
    }

    private fun showAddDogBottomSheet() {
        AddDogBottomSheet { name, imageUrl, description ->
            viewModel.addUserDog(name, imageUrl, description)
        }.show(childFragmentManager, AddDogBottomSheet.TAG)
    }

    private fun showDeleteDogDialog(breed: DogBreed) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_dog_title))
            .setMessage(getString(R.string.delete_dog_message))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteUserDog(breed)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun navigateToDetail(breed: DogBreed) {
        val bundle = Bundle().apply {
            putString("breedName", breed.name)
            putString("imageUrl", breed.imageUrl)
        }
        findNavController().navigate(R.id.action_homeFragment_to_breedDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
