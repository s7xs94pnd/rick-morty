package com.example.rickmorty.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationsResultResponse(
    @SerializedName("info")
    val pageInfo: PagingInfo,
    @SerializedName("results")
    val locationResponseList:List<Location>
)
data class Location(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url:String
)