package com.example.rickmorty.data.repository

import com.example.rickmorty.data.api.CharactersApiService
import com.example.rickmorty.data.dto.Character

class CharactersRepository(private val apiService : CharactersApiService) {
   suspend fun fetchAllCharacters():List<Character>? {
        return if(apiService.fetchAllCharacters().isSuccessful){
            apiService.fetchAllCharacters().body()?.charactersResponseList
        }else{
            emptyList()
        }
    }

    suspend fun fetchCharacterById(id: Int): Character? {
        val response = apiService.fetchCharacterByID(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}