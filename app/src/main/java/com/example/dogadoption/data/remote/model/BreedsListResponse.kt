package com.example.dogadoption.data.remote.model

data class BreedsListResponse(
    val message: Map<String, List<String>>,
    val status: String
)
