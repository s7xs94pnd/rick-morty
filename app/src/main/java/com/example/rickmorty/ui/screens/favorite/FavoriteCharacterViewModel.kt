package com.example.rickmorty.ui.screens.favorite


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.local.model.FavoriteCharacter
import com.example.rickmorty.data.repository.FavoriteCharacterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteCharacterViewModel(private val repository: FavoriteCharacterRepository) : ViewModel() {

    val favoriteCharacters: StateFlow<List<FavoriteCharacter>> = repository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addCharacter(character: FavoriteCharacter) {
        viewModelScope.launch {
            repository.addToFavorites(character)
        }
    }

    fun removeCharacter(character: FavoriteCharacter) {
        viewModelScope.launch {
            repository.removeFromFavorites(character)
        }
    }
}