package com.example.rickmorty.data.remote.api

import com.example.rickmorty.data.remote.dto.Location
import com.example.rickmorty.data.remote.dto.LocationsResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApiService {
    @GET("location")
    suspend fun fetchAllLocations(): Response<LocationsResultResponse>
    @GET("location/{id}")
    suspend fun fetchLocationsByID(@Path("id") id: Int): Response<Location>
}