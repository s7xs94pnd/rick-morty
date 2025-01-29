package com.example.rickmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickmorty.data.remote.api.CharactersApiService
import com.example.rickmorty.data.remote.dto.Character
import com.example.rickmorty.data.remote.paging.CharacterPagingSource

class CharactersRepository(private val apiService: CharactersApiService) {
    fun fetchAllCharacters(): Pager<Int, Character> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                CharacterPagingSource(apiService)
            }
        )
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