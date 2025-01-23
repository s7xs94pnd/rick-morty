package com.example.rickmorty.data.remote.api

import com.example.rickmorty.data.remote.dto.Episode
import com.example.rickmorty.data.remote.dto.EpisodesResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesApiService {
    @GET("episode")
    suspend fun fetchAllEpisodes(): Response<EpisodesResultResponse>
    @GET("episode/{id}")
    suspend fun fetchEpisodesByID(@Path("id") id: Int): Response<Episode>
}