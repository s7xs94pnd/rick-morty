package com.example.rickmorty.ui.screens.characters

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.rickmorty.data.remote.dto.Character
import com.example.rickmorty.ui.components.ItemCard
import com.example.rickmorty.ui.components.ItemList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CharactersScreen(
    navigate: (Int) -> Unit,
    characterViewModel: CharacterViewModel = koinViewModel<CharacterViewModel>()
) {
    LaunchedEffect(Unit) {
        characterViewModel.fetchAllCharacters()
    }
    val characters by characterViewModel.charactersState.collectAsState()
    ItemList(
        items = characters,
        onItemClick = navigate,
        itemContent = { character, onItemClick ->
            CharacterItem(
                character = character as Character,
                navigate = onItemClick,
                onFavoriteClick = { characterViewModel.addToFavorites(it) }
            )
        }
    )
}

@Composable
fun CharacterItem(
    character: Character,
    navigate: (Int) -> Unit,
    onFavoriteClick: (Character) -> Unit
) {
    Row {
        IconButton(
            onClick = { onFavoriteClick(character) }) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Add to favorites"
            )
        }
        ItemCard(
            imageUrl = character.image,
            title = character.name,
            onItemClick = { navigate(character.id) }
        )
    }
}