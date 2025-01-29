package com.example.rickmorty.ui.screens.locations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickmorty.data.remote.dto.Location
import com.example.rickmorty.ui.components.ItemCard
import com.example.rickmorty.ui.components.ItemList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LocationScreen(
    navigate: (Int) -> Unit,
    locationViewModel: LocationViewModel = koinViewModel<LocationViewModel>()
) {
    LaunchedEffect(Unit) {
        locationViewModel.fetchAllLocation()
    }

    val pagingData = locationViewModel.locationState.collectAsLazyPagingItems()

    ItemList(
        pagingData = pagingData,
        onItemClick = navigate,
        itemContent = { location, onItemClick ->
            LocationsItem(location, onItemClick)
        })
}

@Composable
fun LocationsItem(location: Location, navigate: (Int) -> Unit) {
    ItemCard(
        imageUrl = null,
        title = location.name,
        onItemClick = { navigate(location.id) }
    )
}
