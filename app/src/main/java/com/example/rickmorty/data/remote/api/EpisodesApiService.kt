package com.example.rickmorty.data.remote.api

import com.example.rickmorty.data.remote.dto.Episode
import com.example.rickmorty.data.remote.dto.EpisodesResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesApiService {

    @GET("episode")
    suspend fun fetchAllEpisodes(
        @Query("page") page: Int
    ): Response<EpisodesResultResponse>

    @GET("episode/{id}")
    suspend fun fetchEpisodeByID(@Path("id") id: Int): Response<Episode>

    // TODO:
    @GET("episode/{ids}")
    suspend fun fetchEpisodesByIDs(@Path("ids") ids: List<Int>): Response<List<Episode>>
}