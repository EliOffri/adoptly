package com.example.dogadoption.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dogadoption.R
import com.example.dogadoption.databinding.DialogAddDogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddDogBottomSheet(
    private val onAddDog: (name: String, imageUrl: String, description: String) -> Unit
) : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    private var _binding: DialogAddDogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddDogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            val name = binding.editTextDogName.text?.toString()?.trim() ?: ""
            val imageUrl = binding.editTextDogImageUrl.text?.toString()?.trim() ?: ""
            val description = binding.editTextDogDescription.text?.toString()?.trim() ?: ""
            if (name.isNotBlank()) {
                binding.inputLayoutDogName.error = null
                onAddDog(name, imageUrl, description)
                dismiss()
            } else {
                binding.inputLayoutDogName.error = getString(R.string.error_name_required)
            }
        }
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "AddDogBottomSheet"
    }
}
