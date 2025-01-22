package com.example.rickmorty.data.api

import com.example.rickmorty.data.dto.Character
import com.example.rickmorty.data.dto.CharactersResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApiService {
    @GET("character")
    suspend fun fetchAllCharacters(): Response<CharactersResultResponse>

    @GET("character/{id}")
    suspend fun fetchCharacterByID(@Path("id") id: Int): Response<Character>
}