package com.example.dogadoption.data.repository

import com.example.dogadoption.data.local.dao.FavoriteDogDao
import com.example.dogadoption.data.local.entity.FavoriteDogEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val favoriteDogDao: FavoriteDogDao
) {

    fun getAllFavorites(): Flow<List<FavoriteDogEntity>> =
        favoriteDogDao.getAllFavorites()

    fun isFavorite(breedName: String): Flow<Boolean> =
        favoriteDogDao.isFavorite(breedName)

    suspend fun addFavorite(entity: FavoriteDogEntity) =
        favoriteDogDao.insertFavorite(entity)

    suspend fun updateFavorite(entity: FavoriteDogEntity) =
        favoriteDogDao.updateFavorite(entity)

    suspend fun removeFavorite(entity: FavoriteDogEntity) =
        favoriteDogDao.deleteFavorite(entity)

    suspend fun getFavoriteByBreed(breedName: String): FavoriteDogEntity? =
        favoriteDogDao.getFavoriteByBreed(breedName)
}
