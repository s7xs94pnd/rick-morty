package com.example.rickmorty.data.api

import com.example.rickmorty.data.dto.Episode
import com.example.rickmorty.data.dto.EpisodesResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesApiService {
    @GET("episode")
    suspend fun fetchAllEpisodes(): Response<EpisodesResultResponse>
    @GET("episode/{id}")
    suspend fun fetchEpisodesByID(@Path("id") id: Int): Response<Episode>
}