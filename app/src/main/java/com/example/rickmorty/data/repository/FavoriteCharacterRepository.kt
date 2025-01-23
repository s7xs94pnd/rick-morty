package com.example.rickmorty.data.repository

import com.example.rickmorty.data.local.model.FavoriteCharacter
import com.example.rickmorty.data.local.dao.FavoriteCharacterDao
import kotlinx.coroutines.flow.Flow

class FavoriteCharacterRepository(private val dao: FavoriteCharacterDao) {
    suspend fun addToFavorites(character: FavoriteCharacter) {
        dao.insertFavoriteCharacter(character)
    }

    suspend fun removeFromFavorites(character: FavoriteCharacter) {
        dao.deleteFavoriteCharacter(character)
    }

    fun getAllFavorites(): Flow<List<FavoriteCharacter>> {
        return dao.getAllFavoriteCharacters()
    }
}