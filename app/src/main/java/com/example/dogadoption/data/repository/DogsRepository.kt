package com.example.dogadoption.data.repository

import com.example.dogadoption.data.remote.api.DogApiService
import com.example.dogadoption.data.remote.model.DogBreed
import com.example.dogadoption.util.Resource
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogsRepository @Inject constructor(
    private val apiService: DogApiService
) {

    suspend fun getBreedList(): Resource<List<DogBreed>> {
        return try {
            val breedsResponse = apiService.getAllBreeds()
            val breedNames = breedsResponse.message.keys.toList()
            val firstBatch = apiService.getRandomImages(50).message
            val remaining = (breedNames.size - 50).coerceAtLeast(0)
            val secondBatch = if (remaining > 0) {
                apiService.getRandomImages(remaining.coerceAtMost(50)).message
            } else {
                emptyList()
            }
            val images = firstBatch + secondBatch
            val dogBreeds = breedNames.mapIndexed { index, name ->
                DogBreed(
                    name = name,
                    subBreeds = breedsResponse.message[name] ?: emptyList(),
                    imageUrl = images.getOrElse(index) { "" }
                )
            }
            Resource.Success(dogBreeds)
        } catch (e: IOException) {
            Resource.Error(e.message ?: "Network error")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unexpected error")
        }
    }

    suspend fun getBreedImages(breed: String): Resource<List<String>> {
        return try {
            val response = apiService.getBreedImages(breed)
            Resource.Success(response.message)
        } catch (e: IOException) {
            Resource.Error(e.message ?: "Network error")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unexpected error")
        }
    }
}
