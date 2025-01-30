package com.example.rickmorty.ui.screens.locations

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
import com.example.rickmorty.data.remote.dto.Location
import com.example.rickmorty.ui.components.ItemCard
import com.example.rickmorty.ui.components.ItemList
import org.koin.compose.viewmodel.koinViewModel


@OptIn( ExperimentalMaterialApi::class)
@Composable
fun LocationScreen(
    navigate: (Int) -> Unit,
    locationViewModel: LocationViewModel = koinViewModel<LocationViewModel>()
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { locationViewModel.fetchAllLocation() }
    )

    LaunchedEffect(Unit) {
        locationViewModel.fetchAllLocation()
    }

    val pagingData = locationViewModel.locationState.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        ItemList(
            pagingData = pagingData,
            onItemClick = navigate,
            itemContent = { location, onItemClick ->
                LocationsItem(location, onItemClick)
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
fun LocationsItem(location: Location, navigate: (Int) -> Unit) {
    ItemCard(
        imageUrl = null,
        title = location.name,
        onItemClick = { navigate(location.id) }
    )
}
