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
            val count = breedNames.size.coerceAtMost(50)
            val randomImagesResponse = apiService.getRandomImages(count)
            val images = randomImagesResponse.message
            val dogBreeds = breedNames.take(images.size).mapIndexed { index, name ->
                DogBreed(
                    name = name,
                    subBreeds = breedsResponse.message[name] ?: emptyList(),
                    imageUrl = images[index]
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
