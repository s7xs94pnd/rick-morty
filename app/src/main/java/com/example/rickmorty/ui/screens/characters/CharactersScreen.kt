package com.example.rickmorty.ui.screens.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickmorty.data.remote.dto.Character
import com.example.rickmorty.ui.components.ItemCard
import com.example.rickmorty.ui.components.ItemList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharactersScreen(
    navigate: (Int) -> Unit,
    characterViewModel: CharacterViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        characterViewModel.fetchAllCharacters()
    }

    val pagingData = characterViewModel.charactersState.collectAsLazyPagingItems()
    val favorites = characterViewModel.favorites

    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { characterViewModel.fetchAllCharacters() }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        ItemList(
            pagingData = pagingData,
            onItemClick = navigate,
            itemContent = { character, onItemClick ->
                CharacterItem(
                    character = character,
                    navigate = onItemClick,
                    onFavoriteClick = { characterViewModel.addToFavorites(it) },
                    isFavorite = favorites.value.contains(character.id)
                )
            }
        )
        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun CharacterItem(
    character: Character,
    navigate: (Int) -> Unit,
    onFavoriteClick: (Character) -> Unit,
    isFavorite: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onFavoriteClick(character) },
            modifier = Modifier
                .padding(end = 8.dp)
                .size(40.dp)
                .background(
                    color = if (isFavorite) Color.Red else Color.Gray,
                    shape = CircleShape
                ),
            content = {
                Icon(
                    imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Add to favorites",
                    tint = Color.White
                )
            }
        )

        ItemCard(
            imageUrl = character.image,
            title = character.name,
            onItemClick = { navigate(character.id) }
        )
    }
}