package com.example.dogadoption.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dogadoption.data.local.dao.UserStockDao
import com.example.dogadoption.data.local.dao.WatchlistDao
import com.example.dogadoption.data.local.entity.UserStockEntity
import com.example.dogadoption.data.local.entity.WatchlistEntity

@Database(
    entities = [WatchlistEntity::class, UserStockEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
    abstract fun userStockDao(): UserStockDao
}
