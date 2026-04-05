package com.example.dogadoption.ui.reportstray

import android.net.Uri
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
class ReportStrayViewModel @Inject constructor() : ViewModel() {

    private val _photoUri = MutableLiveData<Uri?>()
    val photoUri: LiveData<Uri?> = _photoUri

    private val _location = MutableLiveData<Pair<Double, Double>?>()
    val location: LiveData<Pair<Double, Double>?> = _location

    private val _submissionState = MutableLiveData<Resource<Unit>>()
    val submissionState: LiveData<Resource<Unit>> = _submissionState

    fun setPhotoUri(uri: Uri) {
        _photoUri.value = uri
    }

    fun setLocation(latitude: Double, longitude: Double) {
        _location.value = Pair(latitude, longitude)
    }

    fun clearState() {
        _photoUri.value = null
        _location.value = null
    }

    fun submitReport(description: String) {
        if (description.isBlank()) {
            _submissionState.value = Resource.Error("description")
            return
        }
        if (_photoUri.value == null) {
            _submissionState.value = Resource.Error("photo")
            return
        }
        if (_location.value == null) {
            _submissionState.value = Resource.Error("location")
            return
        }
        _submissionState.value = Resource.Loading()
        viewModelScope.launch {
            delay(1000)
            _submissionState.value = Resource.Success(Unit)
        }
    }
}
