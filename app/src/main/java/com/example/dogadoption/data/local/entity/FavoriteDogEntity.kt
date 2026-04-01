package com.example.dogadoption.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_dogs")
data class FavoriteDogEntity(
    @PrimaryKey val breedName: String,
    val imageUrl: String,
    val notes: String = ""
)
