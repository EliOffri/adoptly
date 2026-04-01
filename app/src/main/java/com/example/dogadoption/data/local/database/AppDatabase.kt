package com.example.dogadoption.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dogadoption.data.local.dao.FavoriteDogDao
import com.example.dogadoption.data.local.dao.UserDogDao
import com.example.dogadoption.data.local.entity.FavoriteDogEntity
import com.example.dogadoption.data.local.entity.UserDogEntity

@Database(
    entities = [FavoriteDogEntity::class, UserDogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDogDao(): FavoriteDogDao
    abstract fun userDogDao(): UserDogDao
}
