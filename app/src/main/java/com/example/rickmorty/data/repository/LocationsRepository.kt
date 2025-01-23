package com.example.rickmorty.data.remote.repository

import com.example.rickmorty.data.remote.api.LocationApiService
import com.example.rickmorty.data.remote.dto.Location

class LocationsRepository(private val apiService : LocationApiService) {
   suspend fun fetchAllLocations(): List<Location>? {
        return if(apiService.fetchAllLocations().isSuccessful){
            apiService.fetchAllLocations().body()?.locationResponseList
        }else{
            emptyList()
        }
    }

    suspend fun fetchLocationById(id: Int): Location? {
        val response = apiService.fetchLocationsByID(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}