package com.example.dogadoption.ui.donation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        setupAmountChips()
        setupSubmitButtonState()
        observeViewModel()

        binding.buttonSubmitDonation.setOnClickListener {
            val name = binding.editTextDonorName.text?.toString()?.trim() ?: ""
            val type = binding.autoCompleteDonationType.text?.toString()?.trim() ?: ""
            val quantity = binding.editTextQuantity.text?.toString()?.trim() ?: ""
            clearErrors()
            viewModel.submitDonation(name, type, quantity)
        }
    }

    private fun setupSubmitButtonState() {
        binding.buttonSubmitDonation.isEnabled = false
        val watcher = object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) { updateSubmitButton() }
        }
        binding.editTextDonorName.addTextChangedListener(watcher)
        binding.autoCompleteDonationType.addTextChangedListener(watcher)
        binding.editTextQuantity.addTextChangedListener(watcher)
    }

    private fun updateSubmitButton() {
        val name = binding.editTextDonorName.text?.toString()?.trim() ?: ""
        val type = binding.autoCompleteDonationType.text?.toString()?.trim() ?: ""
        val qty = binding.editTextQuantity.text?.toString()?.trim()?.toIntOrNull() ?: 0
        binding.buttonSubmitDonation.isEnabled = name.isNotEmpty() && type.isNotEmpty() && qty > 0
    }

    private fun setupDropdown() {
        val donationTypes = resources.getStringArray(R.array.donation_types)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, donationTypes)
        binding.autoCompleteDonationType.setAdapter(adapter)
    }

    private fun setupAmountChips() {
        selectChip(binding.chipAmount50)
        binding.editTextQuantity.setText(getString(R.string.amount_50_value))

        binding.chipAmount25.setOnClickListener {
            selectChip(binding.chipAmount25)
            binding.editTextQuantity.setText(getString(R.string.amount_25_value))
        }
        binding.chipAmount50.setOnClickListener {
            selectChip(binding.chipAmount50)
            binding.editTextQuantity.setText(getString(R.string.amount_50_value))
        }
        binding.chipAmount100.setOnClickListener {
            selectChip(binding.chipAmount100)
            binding.editTextQuantity.setText(getString(R.string.amount_100_value))
        }

        binding.editTextQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s?.toString() ?: ""
                val matchesChip = text == getString(R.string.amount_25_value) ||
                    text == getString(R.string.amount_50_value) ||
                    text == getString(R.string.amount_100_value)
                if (!matchesChip) {
                    clearChipSelection()
                }
            }
        })
    }

    private fun selectChip(selected: android.widget.TextView) {
        val chips = listOf(binding.chipAmount25, binding.chipAmount50, binding.chipAmount100)
        chips.forEach { chip ->
            if (chip == selected) {
                chip.setBackgroundResource(R.drawable.bg_amount_chip_selected)
                chip.setTextColor(requireContext().getColor(R.color.primary))
            } else {
                chip.setBackgroundResource(R.drawable.bg_amount_chip_default)
                chip.setTextColor(requireContext().getColor(R.color.text_muted))
            }
        }
    }

    private fun clearChipSelection() {
        listOf(binding.chipAmount25, binding.chipAmount50, binding.chipAmount100).forEach { chip ->
            chip.setBackgroundResource(R.drawable.bg_amount_chip_default)
            chip.setTextColor(requireContext().getColor(R.color.text_muted))
        }
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
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    clearFields()
                    Snackbar.make(binding.root, getString(R.string.donation_success), Snackbar.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    updateSubmitButton()
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
        binding.editTextNote.text?.clear()
        selectChip(binding.chipAmount50)
        binding.editTextQuantity.setText(getString(R.string.amount_50_value))
        clearErrors()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
