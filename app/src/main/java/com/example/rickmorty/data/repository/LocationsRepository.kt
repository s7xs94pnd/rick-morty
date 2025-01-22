package com.example.rickmorty.data.repository

import com.example.rickmorty.data.api.LocationApiService
import com.example.rickmorty.data.dto.Location

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