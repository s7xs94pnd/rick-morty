package com.example.rickmorty.data.remote.api

import com.example.rickmorty.data.remote.dto.Character
import com.example.rickmorty.data.remote.dto.CharactersResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApiService {

    @GET("character")
    suspend fun fetchAllCharacters(
        @Query("page") page: Int
    ): Response<CharactersResultResponse>

    @GET("character/{id}")
    suspend fun fetchCharacterByID(@Path("id") id: Int): Response<Character>

    // TODO:
    @GET("character/{ids}")
    suspend fun fetchCharactersByIDs(@Path("ids") ids: List<Int>): Response<List<Character>>
}