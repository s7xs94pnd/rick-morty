package com.example.rickmorty.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PagingInfo (
    @SerializedName("page")
    val page : Int,
    @SerializedName("count")
    val count :Int,
    @SerializedName("next")
    val nextPage : String?,
    @SerializedName("prev")
    val prevPage : String?
)