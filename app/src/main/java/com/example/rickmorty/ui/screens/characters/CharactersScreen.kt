package com.example.rickmorty.ui.screens.characters

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.rickmorty.data.dto.Character
import com.example.rickmorty.ui.components.ItemCard
import com.example.rickmorty.ui.components.ItemList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CharactersScreen(
    navigate: (Int) -> Unit,
    characterViewModel: CharacterViewModel = koinViewModel<CharacterViewModel>()
) {
    val characters by characterViewModel.charactersState.collectAsState()
    Log.e("CharactersScreen", "Characters list size: ${characters.size}")
    ItemList(characters, onItemClick = navigate,
        itemContent = { character, onItemClick ->
            CharacterItem(character as Character, onItemClick)
        })
}

@Composable
fun CharacterItem(character: Character, navigate: (Int) -> Unit) {
    ItemCard(
        imageUrl = character.image,
        title = character.name,
        onItemClick = { navigate(character.id) }
    )
}