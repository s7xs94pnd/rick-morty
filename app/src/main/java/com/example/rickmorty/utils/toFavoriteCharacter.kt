package com.example.rickmorty.utils

import com.example.rickmorty.data.local.model.FavoriteCharacter
import com.example.rickmorty.data.remote.dto.Character

fun Character.toFavoriteCharacter(): FavoriteCharacter {
    return FavoriteCharacter(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        locationName = this.location.name,
        image = this.image,
        isFavorite = false
    )
}