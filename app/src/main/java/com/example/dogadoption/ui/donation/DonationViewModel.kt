package com.example.dogadoption.ui.donation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogadoption.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor() : ViewModel() {

    private val _submissionState = MutableLiveData<Resource<Unit>>()
    val submissionState: LiveData<Resource<Unit>> = _submissionState

    fun submitDonation(donorName: String, donationType: String, quantity: String) {
        if (donorName.isBlank()) {
            _submissionState.value = Resource.Error("name")
            return
        }
        if (donationType.isBlank()) {
            _submissionState.value = Resource.Error("type")
            return
        }
        val qty = quantity.toIntOrNull()
        if (qty == null || qty <= 0) {
            _submissionState.value = Resource.Error("quantity")
            return
        }
        _submissionState.value = Resource.Loading()
        viewModelScope.launch {
            delay(1000)
            _submissionState.value = Resource.Success(Unit)
        }
    }
}
