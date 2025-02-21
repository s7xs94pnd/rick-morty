package com.example.rickmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickmorty.data.remote.api.LocationApiService
import com.example.rickmorty.data.remote.dto.Location
import com.example.rickmorty.data.remote.paging.LocationPagingSource

class LocationsRepository(private val apiService : LocationApiService) {
    fun fetchAllLocations(): Pager<Int,Location> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                initialLoadSize = 100
            ),
            pagingSourceFactory = {
                LocationPagingSource(apiService)
            }
        )
    }

    suspend fun fetchLocationById(id: Int): Location? {
        val response = apiService.fetchLocationByID(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}