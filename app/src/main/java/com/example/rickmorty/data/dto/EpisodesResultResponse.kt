package com.example.rickmorty.data.dto

import com.google.gson.annotations.SerializedName

data class EpisodesResultResponse(
    @SerializedName("results")
    val episodesResponseList:List<Episode>
)

data class Episode(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url:String
)