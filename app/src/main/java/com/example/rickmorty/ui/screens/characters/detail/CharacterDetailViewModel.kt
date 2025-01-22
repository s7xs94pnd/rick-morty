package com.example.rickmorty.ui.screens.characters.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.dto.Character
import com.example.rickmorty.data.repository.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val charactersRepository: CharactersRepository,
    private val characterId: Int
) : ViewModel() {

    private val _characterState = MutableStateFlow<Character?>(null)
    val characterState: StateFlow<Character?> = _characterState.asStateFlow()

    init {
        fetchCharacterById()
    }

    private fun fetchCharacterById() {
        viewModelScope.launch {
            val character = charactersRepository.fetchCharacterById(characterId)
            _characterState.value = character
        }
    }
}