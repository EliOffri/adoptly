package com.example.dogadoption.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dogadoption.R
import com.example.dogadoption.data.remote.model.DogBreed
import com.example.dogadoption.databinding.DialogAddDogBinding
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
        observeViewModel()
        binding.fabAddDog.setOnClickListener { showAddDogDialog() }
    }

    private fun setupRecyclerView() {
        adapter = DogBreedAdapter(
            onBreedClicked = { breed -> navigateToDetail(breed) },
            onBreedLongClicked = { breed -> showDeleteDogDialog(breed) }
        )
        binding.recyclerViewBreeds.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewBreeds.adapter = adapter
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
                    adapter.submitList(resource.data)
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

    private fun showAddDogDialog() {
        val dialogBinding = DialogAddDogBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.add_dog_dialog_title))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.add)) { _, _ ->
                val name = dialogBinding.editTextDogName.text?.toString()?.trim() ?: ""
                val imageUrl = dialogBinding.editTextDogImageUrl.text?.toString()?.trim() ?: ""
                val description = dialogBinding.editTextDogDescription.text?.toString()?.trim() ?: ""
                if (name.isNotBlank()) {
                    viewModel.addUserDog(name, imageUrl, description)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
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
