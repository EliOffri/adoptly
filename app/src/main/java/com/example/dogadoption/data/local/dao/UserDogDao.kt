package com.example.dogadoption.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dogadoption.data.local.entity.UserDogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDogDao {

    @Query("SELECT * FROM user_dogs ORDER BY id DESC")
    fun getAllUserDogs(): Flow<List<UserDogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDog(entity: UserDogEntity)

    @Delete
    suspend fun deleteUserDog(entity: UserDogEntity)
}
