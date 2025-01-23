package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.data.local.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCharacter(character: FavoriteCharacter)

    @Delete
    suspend fun deleteFavoriteCharacter(character: FavoriteCharacter)

    @Query("SELECT * FROM favorite_characters")
    fun getAllFavoriteCharacters(): Flow<List<FavoriteCharacter>>
}