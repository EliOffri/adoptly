package com.example.stockly.ui.donation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.stockly.R
import com.example.stockly.databinding.FragmentPriceAlertBinding
import com.example.stockly.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceAlertFragment : Fragment() {

    private var _binding: FragmentPriceAlertBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PriceAlertViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDropdown()
        setupThresholdChips()
        setupSubmitButtonState()
        observeViewModel()

        binding.buttonSubmitAlert.setOnClickListener {
            val symbol = binding.editTextSymbol.text?.toString()?.trim() ?: ""
            val condition = binding.autoCompleteCondition.text?.toString()?.trim() ?: ""
            val price = binding.editTextQuantity.text?.toString()?.trim() ?: ""
            clearErrors()
            viewModel.submitAlert(symbol, condition, price)
        }
    }

    private fun setupSubmitButtonState() {
        binding.buttonSubmitAlert.isEnabled = false
        val watcher = object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) { updateSubmitButton() }
        }
        binding.editTextSymbol.addTextChangedListener(watcher)
        binding.autoCompleteCondition.addTextChangedListener(watcher)
        binding.editTextQuantity.addTextChangedListener(watcher)
    }

    private fun updateSubmitButton() {
        val symbol = binding.editTextSymbol.text?.toString()?.trim() ?: ""
        val condition = binding.autoCompleteCondition.text?.toString()?.trim() ?: ""
        val price = binding.editTextQuantity.text?.toString()?.trim()?.toDoubleOrNull() ?: 0.0
        binding.buttonSubmitAlert.isEnabled = symbol.isNotEmpty() && condition.isNotEmpty() && price > 0.0
    }

    private fun setupDropdown() {
        val alertConditions = resources.getStringArray(R.array.alert_conditions)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, alertConditions)
        binding.autoCompleteCondition.setAdapter(adapter)
    }

    private fun setupThresholdChips() {
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
                if (!matchesChip) clearChipSelection()
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
        binding.inputLayoutSymbol.error = null
        binding.inputLayoutCondition.error = null
        binding.inputLayoutQuantity.error = null
    }

    private fun observeViewModel() {
        viewModel.submissionState.observe(viewLifecycleOwner) { resource ->
            resource ?: return@observe
            when (resource) {
                is Resource.Loading -> {
                    binding.buttonSubmitAlert.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    clearFields()
                    Snackbar.make(binding.root, getString(R.string.alert_success), Snackbar.LENGTH_LONG).show()
                    viewModel.resetState()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    updateSubmitButton()
                    when (resource.message) {
                        "symbol" -> binding.inputLayoutSymbol.error = getString(R.string.error_symbol_required)
                        "condition" -> binding.inputLayoutCondition.error = getString(R.string.error_condition_required)
                        "price" -> binding.inputLayoutQuantity.error = getString(R.string.error_invalid_price)
                        else -> Snackbar.make(binding.root, getString(R.string.error_loading_data), Snackbar.LENGTH_LONG).show()
                    }
                    viewModel.resetState()
                }
            }
        }
    }

    private fun clearFields() {
        binding.editTextSymbol.text?.clear()
        binding.autoCompleteCondition.text?.clear()
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
