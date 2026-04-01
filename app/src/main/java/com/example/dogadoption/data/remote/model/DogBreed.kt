package com.example.dogadoption.data.remote.model

data class DogBreed(
    val name: String,
    val subBreeds: List<String>,
    val imageUrl: String,
    val isLocallyAdded: Boolean = false,
    val localId: Int? = null
)
