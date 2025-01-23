package com.example.rickmorty.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickmorty.data.local.model.FavoriteCharacter
import com.example.rickmorty.data.local.dao.FavoriteCharacterDao

@Database(entities = [FavoriteCharacter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}