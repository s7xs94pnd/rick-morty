package com.example.rickmorty.data.repository

import com.example.rickmorty.data.api.EpisodesApiService
import com.example.rickmorty.data.dto.Episode

class EpisodesRepository(private val apiService: EpisodesApiService) {
    suspend fun fetchAllEpisodes(): List<Episode>? {
        return if (apiService.fetchAllEpisodes().isSuccessful) {
            apiService.fetchAllEpisodes().body()?.episodesResponseList
        } else {
            emptyList()
        }
    }

    suspend fun fetchEpisodesById(id: Int):Episode?{
        return if(apiService.fetchEpisodesByID(id).isSuccessful)
            apiService.fetchEpisodesByID(id).body()
        else
            null
    }
}