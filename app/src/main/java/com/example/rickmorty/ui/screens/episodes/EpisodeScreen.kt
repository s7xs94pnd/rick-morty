package com.example.rickmorty.ui.screens.episodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickmorty.data.remote.dto.Episode
import com.example.rickmorty.ui.components.ItemCard
import com.example.rickmorty.ui.components.ItemList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EpisodeScreen(
    navigate: (Int) -> Unit,
    episodeViewModel: EpisodeViewModel = koinViewModel()
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { episodeViewModel.fetchAllEpisodes() }
    )

    LaunchedEffect(Unit) {
        episodeViewModel.fetchAllEpisodes()
    }

    val pagingData = episodeViewModel.episodeFlow.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
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

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
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