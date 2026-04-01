package com.example.dogadoption.ui.breeddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogadoption.data.local.entity.FavoriteDogEntity
import com.example.dogadoption.data.repository.DogsRepository
import com.example.dogadoption.data.repository.FavoritesRepository
import com.example.dogadoption.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _imagesState = MutableLiveData<Resource<List<String>>>()
    val imagesState: LiveData<Resource<List<String>>> = _imagesState

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var currentBreedName: String = ""
    private var currentImageUrl: String = ""

    fun loadBreedDetails(breedName: String, imageUrl: String) {
        currentBreedName = breedName
        currentImageUrl = imageUrl
        _imagesState.value = Resource.Loading()
        viewModelScope.launch {
            _imagesState.value = dogsRepository.getBreedImages(breedName)
        }
        viewModelScope.launch {
            favoritesRepository.isFavorite(breedName).collect { fav ->
                _isFavorite.postValue(fav)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val existing = favoritesRepository.getFavoriteByBreed(currentBreedName)
            if (existing != null) {
                favoritesRepository.removeFavorite(existing)
            } else {
                favoritesRepository.addFavorite(
                    FavoriteDogEntity(
                        breedName = currentBreedName,
                        imageUrl = currentImageUrl
                    )
                )
            }
        }
    }
}
