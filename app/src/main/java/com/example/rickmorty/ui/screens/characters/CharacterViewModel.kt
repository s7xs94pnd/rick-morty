package com.example.rickmorty.ui.screens.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.dto.Character
import com.example.rickmorty.data.repository.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModel (private val charactersRepository: CharactersRepository) : ViewModel() {

    private val _charactersState = MutableStateFlow<List<Character>>(emptyList())
    val charactersState: StateFlow<List<Character>> = _charactersState.asStateFlow()

    init {
        fetchAllCharacters()
    }
    private fun fetchAllCharacters() {
        viewModelScope.launch {
            val characters = charactersRepository.fetchAllCharacters()
            if (characters != null) {
                _charactersState.value = characters
            } else {
                Log.e("CharacterViewModel", "Failed to fetch characters")
            }
        }
    }
}