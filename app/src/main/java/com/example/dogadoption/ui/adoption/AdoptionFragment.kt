package com.example.dogadoption.ui.adoption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogadoption.R
import com.example.dogadoption.databinding.FragmentAdoptionBinding
import com.example.dogadoption.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdoptionFragment : Fragment() {

    private var _binding: FragmentAdoptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdoptionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdoptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        val breedName = arguments?.getString("breedName") ?: ""
        binding.textBreedName.text = breedName.replaceFirstChar { it.uppercase() }

        observeViewModel(breedName)

        binding.buttonSubmit.setOnClickListener {
            val name = binding.editTextName.text?.toString()?.trim() ?: ""
            val email = binding.editTextEmail.text?.toString()?.trim() ?: ""
            val phone = binding.editTextPhone.text?.toString()?.trim() ?: ""
            clearErrors()
            viewModel.submitAdoption(name, email, phone, breedName)
        }
    }

    private fun clearErrors() {
        binding.inputLayoutName.error = null
        binding.inputLayoutEmail.error = null
        binding.inputLayoutPhone.error = null
    }

    private fun observeViewModel(breedName: String) {
        viewModel.submissionState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.buttonSubmit.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.buttonSubmit.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, getString(R.string.adoption_success), Snackbar.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
                is Resource.Error -> {
                    binding.buttonSubmit.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    when (resource.message) {
                        "name" -> binding.inputLayoutName.error = getString(R.string.error_name_required)
                        "email" -> binding.inputLayoutEmail.error = getString(R.string.error_invalid_email)
                        "phone" -> binding.inputLayoutPhone.error = getString(R.string.error_invalid_phone)
                        else -> Snackbar.make(binding.root, getString(R.string.error_loading_data), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
