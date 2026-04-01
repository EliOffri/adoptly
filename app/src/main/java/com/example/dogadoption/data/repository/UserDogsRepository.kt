package com.example.dogadoption.data.repository

import com.example.dogadoption.data.local.dao.UserDogDao
import com.example.dogadoption.data.local.entity.UserDogEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDogsRepository @Inject constructor(
    private val userDogDao: UserDogDao
) {

    fun getAllUserDogs(): Flow<List<UserDogEntity>> =
        userDogDao.getAllUserDogs()

    suspend fun addUserDog(entity: UserDogEntity) =
        userDogDao.insertUserDog(entity)

    suspend fun deleteUserDog(entity: UserDogEntity) =
        userDogDao.deleteUserDog(entity)
}
