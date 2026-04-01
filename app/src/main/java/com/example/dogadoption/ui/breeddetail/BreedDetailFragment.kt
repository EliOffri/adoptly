package com.example.dogadoption.ui.breeddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dogadoption.R
import com.example.dogadoption.databinding.FragmentBreedDetailBinding
import com.example.dogadoption.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedDetailFragment : Fragment() {

    private var _binding: FragmentBreedDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BreedDetailViewModel by viewModels()
    private lateinit var imageAdapter: BreedImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breedName = arguments?.getString("breedName") ?: ""
        val imageUrl = arguments?.getString("imageUrl") ?: ""

        setupRecyclerView()
        setupBreedInfo(breedName, imageUrl)
        observeViewModel()

        viewModel.loadBreedDetails(breedName, imageUrl)

        binding.fabFavorite.setOnClickListener { viewModel.toggleFavorite() }

        binding.buttonAdopt.setOnClickListener {
            val bundle = Bundle().apply { putString("breedName", breedName) }
            findNavController().navigate(R.id.action_breedDetailFragment_to_adoptionFragment, bundle)
        }
    }

    private fun setupRecyclerView() {
        imageAdapter = BreedImageAdapter()
        binding.recyclerViewImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewImages.adapter = imageAdapter
    }

    private fun setupBreedInfo(breedName: String, imageUrl: String) {
        binding.textBreedTitle.text = breedName.replaceFirstChar { it.uppercase() }
        binding.textSubBreeds.text = getString(R.string.no_sub_breeds)
        Glide.with(this)
            .load(imageUrl.ifBlank { null })
            .placeholder(R.drawable.ic_placeholder_dog)
            .error(R.drawable.ic_placeholder_dog)
            .centerCrop()
            .into(binding.imageHero)
    }

    private fun observeViewModel() {
        viewModel.imagesState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    imageAdapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, getString(R.string.error_loading_data), Snackbar.LENGTH_LONG).show()
                }
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFav ->
            val icon = if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            binding.fabFavorite.setImageResource(icon)
            val desc = if (isFav) getString(R.string.remove_from_favorites) else getString(R.string.add_to_favorites)
            binding.fabFavorite.contentDescription = desc
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
