package com.example.dogadoption.data.remote.api

import com.example.dogadoption.data.remote.model.BreedImagesResponse
import com.example.dogadoption.data.remote.model.BreedsListResponse
import com.example.dogadoption.data.remote.model.RandomImagesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedsListResponse

    @GET("breed/{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed: String): BreedImagesResponse

    @GET("breeds/image/random/{count}")
    suspend fun getRandomImages(@Path("count") count: Int): RandomImagesResponse
}
