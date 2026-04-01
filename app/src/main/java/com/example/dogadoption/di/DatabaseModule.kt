package com.example.dogadoption.di

import android.content.Context
import androidx.room.Room
import com.example.dogadoption.data.local.dao.FavoriteDogDao
import com.example.dogadoption.data.local.dao.UserDogDao
import com.example.dogadoption.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "dog_adoption_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideFavoriteDogDao(database: AppDatabase): FavoriteDogDao =
        database.favoriteDogDao()

    @Provides
    fun provideUserDogDao(database: AppDatabase): UserDogDao =
        database.userDogDao()
}
