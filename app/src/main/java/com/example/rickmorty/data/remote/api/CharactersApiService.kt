package com.example.rickmorty.data.remote.api

import com.example.rickmorty.data.remote.dto.Character
import com.example.rickmorty.data.remote.dto.CharactersResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApiService {
    @GET("character")
    suspend fun fetchAllCharacters(): Response<CharactersResultResponse>

    @GET("character/{id}")
    suspend fun fetchCharacterByID(@Path("id") id: Int): Response<Character>
}