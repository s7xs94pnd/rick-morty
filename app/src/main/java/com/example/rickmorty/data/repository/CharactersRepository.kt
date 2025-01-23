package com.example.rickmorty.data.remote.repository

import com.example.rickmorty.data.remote.api.CharactersApiService
import com.example.rickmorty.data.remote.dto.Character

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