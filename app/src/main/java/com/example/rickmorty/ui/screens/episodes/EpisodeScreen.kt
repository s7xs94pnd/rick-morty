package com.example.rickmorty.ui.screens.episodes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickmorty.data.remote.dto.Episode
import com.example.rickmorty.ui.components.ItemCard
import com.example.rickmorty.ui.components.ItemList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EpisodeScreen(
    navigate: (Int) -> Unit,
    episodeViewModel: EpisodeViewModel = koinViewModel<EpisodeViewModel>()
) {
    LaunchedEffect(Unit) {
        episodeViewModel.fetchAllEpisodes()
    }
    val pagingData = episodeViewModel.episodeFlow.collectAsLazyPagingItems()

    ItemList(
        pagingData = pagingData,
        onItemClick = navigate,
        itemContent = { episode, onItemClick ->
            EpisodeItem(
                episode = episode,
                navigate = onItemClick
            )
        }
    )
}

@Composable
fun EpisodeItem(episode: Episode, navigate: (Int) -> Unit) {
    ItemCard(
        onItemClick = {
            navigate(episode.id)
        },
        title = episode.name,
        imageUrl = null
    )
}