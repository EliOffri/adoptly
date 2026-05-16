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
class PriceAlertViewModel @Inject constructor() : ViewModel() {

    private val _submissionState = MutableLiveData<Resource<Unit>>()
    val submissionState: LiveData<Resource<Unit>> = _submissionState

    fun submitAlert(symbol: String, condition: String, targetPrice: String) {
        if (symbol.isBlank()) {
            _submissionState.value = Resource.Error("symbol")
            return
        }
        if (condition.isBlank()) {
            _submissionState.value = Resource.Error("condition")
            return
        }
        val price = targetPrice.toDoubleOrNull()
        if (price == null || price <= 0.0) {
            _submissionState.value = Resource.Error("price")
            return
        }
        _submissionState.value = Resource.Loading()
        viewModelScope.launch {
            delay(1000)
            _submissionState.value = Resource.Success(Unit)
        }
    }
}
