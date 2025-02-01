package com.example.rickmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickmorty.data.remote.api.EpisodesApiService
import com.example.rickmorty.data.remote.dto.Episode
import com.example.rickmorty.data.remote.paging.CharacterPagingSource
import com.example.rickmorty.data.remote.paging.EpisodePagingSource

class EpisodesRepository(private val apiService: EpisodesApiService) {
    fun fetchAllEpisodes(): Pager<Int,Episode> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                initialLoadSize = 100
            ),
            pagingSourceFactory = {
                EpisodePagingSource(apiService)
            }
        )
    }

    suspend fun fetchEpisodesById(id: Int): Episode? {
        return if (apiService.fetchEpisodesByID(id).isSuccessful)
            apiService.fetchEpisodesByID(id).body()
        else
            null
    }
}

