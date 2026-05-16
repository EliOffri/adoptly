package com.example.dogadoption.data.repository

import com.example.dogadoption.data.local.dao.UserStockDao
import com.example.dogadoption.data.local.entity.UserStockEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStocksRepository @Inject constructor(
    private val userStockDao: UserStockDao
) {

    fun getAllUserStocks(): Flow<List<UserStockEntity>> =
        userStockDao.getAllUserStocks()

    suspend fun addUserStock(entity: UserStockEntity) =
        userStockDao.insertUserStock(entity)

    suspend fun deleteUserStock(entity: UserStockEntity) =
        userStockDao.deleteUserStock(entity)
}
