package com.example.dogadoption.ui.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dogadoption.R
import com.example.dogadoption.databinding.FragmentDonationBinding
import com.example.dogadoption.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationFragment : Fragment() {

    private var _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DonationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDropdown()
        observeViewModel()

        binding.buttonSubmitDonation.setOnClickListener {
            val name = binding.editTextDonorName.text?.toString()?.trim() ?: ""
            val type = binding.autoCompleteDonationType.text?.toString()?.trim() ?: ""
            val quantity = binding.editTextQuantity.text?.toString()?.trim() ?: ""
            clearErrors()
            viewModel.submitDonation(name, type, quantity)
        }
    }

    private fun setupDropdown() {
        val donationTypes = resources.getStringArray(R.array.donation_types)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, donationTypes)
        binding.autoCompleteDonationType.setAdapter(adapter)
    }

    private fun clearErrors() {
        binding.inputLayoutDonorName.error = null
        binding.inputLayoutDonationType.error = null
        binding.inputLayoutQuantity.error = null
    }

    private fun observeViewModel() {
        viewModel.submissionState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.buttonSubmitDonation.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.buttonSubmitDonation.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    clearFields()
                    Snackbar.make(binding.root, getString(R.string.donation_success), Snackbar.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    binding.buttonSubmitDonation.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    when (resource.message) {
                        "name" -> binding.inputLayoutDonorName.error = getString(R.string.error_name_empty)
                        "type" -> binding.inputLayoutDonationType.error = getString(R.string.error_type_empty)
                        "quantity" -> binding.inputLayoutQuantity.error = getString(R.string.error_invalid_quantity)
                        else -> Snackbar.make(binding.root, getString(R.string.error_loading_data), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun clearFields() {
        binding.editTextDonorName.text?.clear()
        binding.autoCompleteDonationType.text?.clear()
        binding.editTextQuantity.text?.clear()
        clearErrors()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
