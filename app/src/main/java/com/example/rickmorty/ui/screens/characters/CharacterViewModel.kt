package com.example.rickmorty.ui.screens.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.rickmorty.data.repository.FavoriteCharacterRepository
import com.example.rickmorty.data.remote.dto.Character
import com.example.rickmorty.data.repository.CharactersRepository
import com.example.rickmorty.utils.toFavoriteCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val charactersRepository: CharactersRepository,
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : ViewModel() {

    private val _charactersState = MutableSharedFlow<PagingData<Character>>()
    val charactersState: SharedFlow<PagingData<Character>> = _charactersState.asSharedFlow()

    fun fetchAllCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val characters = charactersRepository.fetchAllCharacters()
            if (characters != null) {
                charactersRepository.fetchAllCharacters()
                    .flow
                    .collectLatest {pagingData->
                        _charactersState.emit( pagingData)
                    }
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

