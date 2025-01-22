package com.example.rickmorty.data.dto

import com.google.gson.annotations.SerializedName

data class CharactersResultResponse(
    @SerializedName("results")
    val charactersResponseList:List<Character>
)

data class Character (
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("status")
    val status : String,
    @SerializedName("species")
    val species : String,
    @SerializedName("type")
    val type : String,
    @SerializedName("gender")
    val gender : String,
    @SerializedName("location")
    val location : Location,
    @SerializedName("image")
    val image : String
)

