package com.example.rickmorty.ui.screens.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.repository.FavoriteCharacterRepository
import com.example.rickmorty.data.remote.dto.Character
import com.example.rickmorty.data.repository.CharactersRepository
import com.example.rickmorty.utils.toFavoriteCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val charactersRepository: CharactersRepository,
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : ViewModel() {

    private val _charactersState = MutableStateFlow<List<Character>>(emptyList())
    val charactersState: StateFlow<List<Character>> = _charactersState.asStateFlow()

    fun fetchAllCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val characters = charactersRepository.fetchAllCharacters()
            if (characters != null) {
                _charactersState.value = characters
            } else {
                Log.e("CharacterViewModel", "Failed to fetch characters")
            }
        }
    }

    fun addToFavorites(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteCharacter = character.toFavoriteCharacter()
            favoriteCharacter.isFavorite = true
            favoriteCharacterRepository.addToFavorites(favoriteCharacter)
        }
    }
}

