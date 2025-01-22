package com.example.rickmorty.ui.screens.locations

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.rickmorty.data.dto.Location
import com.example.rickmorty.ui.components.ItemCard
import com.example.rickmorty.ui.components.ItemList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LocationScreen(
    navigate: (Int) -> Unit,
    locationViewModel: LocationViewModel = koinViewModel<LocationViewModel>()
) {
    val locations by locationViewModel.locationState.collectAsState()
    Log.e("locationsScreen", "locations list size: ${locations.size}")
    ItemList(locations, onItemClick = navigate,
        itemContent = { location, onItemClick ->
            LocationsItem(location as Location, onItemClick)
        })
}

@Composable
fun LocationsItem(location: Location, navigate: (Int) -> Unit) {
    ItemCard(
        imageUrl =null,
        title = location.name,
        onItemClick = { navigate(location.id) }
    )
}
