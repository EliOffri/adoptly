package com.example.dogadoption.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.dogadoption.data.local.entity.UserDogEntity
import com.example.dogadoption.data.remote.model.DogBreed
import com.example.dogadoption.data.repository.DogsRepository
import com.example.dogadoption.data.repository.UserDogsRepository
import com.example.dogadoption.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
    private val userDogsRepository: UserDogsRepository
) : ViewModel() {

    private val _apiBreeds = MutableLiveData<Resource<List<DogBreed>>>(Resource.Loading())

    val combinedList: LiveData<Resource<List<DogBreed>>> =
        userDogsRepository.getAllUserDogs().asLiveData().switchMap { userDogs ->
            _apiBreeds.map { apiState ->
                when (apiState) {
                    is Resource.Success -> {
                        val localItems = userDogs.map { it.toDogBreed() }
                        Resource.Success(localItems + (apiState.data ?: emptyList()))
                    }
                    is Resource.Error -> apiState
                    is Resource.Loading -> apiState
                }
            }
        }

    init {
        loadBreeds()
    }

    fun loadBreeds() {
        _apiBreeds.value = Resource.Loading()
        viewModelScope.launch {
            _apiBreeds.value = dogsRepository.getBreedList()
        }
    }

    fun addUserDog(name: String, imageUrl: String, description: String) {
        viewModelScope.launch {
            userDogsRepository.addUserDog(
                UserDogEntity(name = name, imageUrl = imageUrl, description = description)
            )
        }
    }

    fun deleteUserDog(dogBreed: DogBreed) {
        val id = dogBreed.localId ?: return
        viewModelScope.launch {
            userDogsRepository.deleteUserDog(
                UserDogEntity(id = id, name = dogBreed.name, imageUrl = dogBreed.imageUrl, description = "")
            )
        }
    }

    private fun UserDogEntity.toDogBreed(): DogBreed =
        DogBreed(
            name = name,
            subBreeds = emptyList(),
            imageUrl = imageUrl,
            isLocallyAdded = true,
            localId = id
        )
}
