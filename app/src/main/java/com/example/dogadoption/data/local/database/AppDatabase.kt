package com.example.stockly.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockly.data.local.dao.UserStockDao
import com.example.stockly.data.local.dao.WatchlistDao
import com.example.stockly.data.local.entity.UserStockEntity
import com.example.stockly.data.local.entity.WatchlistEntity

@Database(
    entities = [WatchlistEntity::class, UserStockEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
    abstract fun userStockDao(): UserStockDao
}
