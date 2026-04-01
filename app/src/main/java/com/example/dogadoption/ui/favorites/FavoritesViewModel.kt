package com.example.dogadoption.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dogadoption.data.local.entity.FavoriteDogEntity
import com.example.dogadoption.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    val favorites: LiveData<List<FavoriteDogEntity>> =
        favoritesRepository.getAllFavorites().asLiveData()

    fun removeFavorite(entity: FavoriteDogEntity) {
        viewModelScope.launch {
            favoritesRepository.removeFavorite(entity)
        }
    }

    fun updateNotes(entity: FavoriteDogEntity, newNotes: String) {
        viewModelScope.launch {
            favoritesRepository.updateFavorite(entity.copy(notes = newNotes))
        }
    }
}
