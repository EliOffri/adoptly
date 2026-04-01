package com.example.dogadoption.ui.adoption

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogadoption.data.repository.FavoritesRepository
import com.example.dogadoption.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdoptionViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _submissionState = MutableLiveData<Resource<Unit>>()
    val submissionState: LiveData<Resource<Unit>> = _submissionState

    fun submitAdoption(applicantName: String, email: String, phone: String, breedName: String) {
        if (applicantName.isBlank()) {
            _submissionState.value = Resource.Error("name")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _submissionState.value = Resource.Error("email")
            return
        }
        if (phone.length < 7 || phone.any { !it.isDigit() && it != '+' && it != '-' }) {
            _submissionState.value = Resource.Error("phone")
            return
        }
        _submissionState.value = Resource.Loading()
        viewModelScope.launch {
            delay(1000)
            favoritesRepository.getFavoriteByBreed(breedName)?.let { entity ->
                favoritesRepository.removeFavorite(entity)
            }
            _submissionState.value = Resource.Success(Unit)
        }
    }
}
