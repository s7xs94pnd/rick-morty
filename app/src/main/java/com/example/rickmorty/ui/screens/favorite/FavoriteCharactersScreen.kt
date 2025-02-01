package com.example.rickmorty.ui.screens.favorite

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rickmorty.data.local.model.FavoriteCharacter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoriteCharactersScreen(viewModel: FavoriteCharacterViewModel = koinViewModel<FavoriteCharacterViewModel>()) {
    val favoriteCharacters by viewModel.favoriteCharacters.collectAsState()

    LazyColumn {
        items(favoriteCharacters) { character ->
            FavoriteCharacterItem(character,
                onAddToFavorites = { viewModel.addCharacter(character) },
                onRemoveFromFavorites = { viewModel.removeCharacter(character) }
            )
        }
    }
}

@Composable
fun FavoriteCharacterItem(
    character: FavoriteCharacter,
    onAddToFavorites: () -> Unit,
    onRemoveFromFavorites: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = {
                if (character.isFavorite) {
                    onRemoveFromFavorites()
                } else {
                    onAddToFavorites()
                }
            }
        ) {
            Icon(
                imageVector = if (character.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (character.isFavorite) "Remove from Favorites" else "Add to Favorites"
            )
        }
        Text(text = character.name, style = MaterialTheme.typography.bodyLarge)
    }
}