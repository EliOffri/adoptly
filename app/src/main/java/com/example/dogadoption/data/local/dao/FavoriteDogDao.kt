package com.example.dogadoption.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dogadoption.data.local.entity.FavoriteDogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDogDao {

    @Query("SELECT * FROM favorite_dogs ORDER BY breedName ASC")
    fun getAllFavorites(): Flow<List<FavoriteDogEntity>>

    @Query("SELECT * FROM favorite_dogs WHERE breedName = :breedName")
    suspend fun getFavoriteByBreed(breedName: String): FavoriteDogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(entity: FavoriteDogEntity)

    @Update
    suspend fun updateFavorite(entity: FavoriteDogEntity)

    @Delete
    suspend fun deleteFavorite(entity: FavoriteDogEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_dogs WHERE breedName = :breedName)")
    fun isFavorite(breedName: String): Flow<Boolean>
}
