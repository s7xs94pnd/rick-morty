package com.example.rickmorty.ui.servisceLocator

import com.example.rickmorty.ui.screens.characters.CharacterViewModel
import com.example.rickmorty.ui.screens.characters.detail.CharacterDetailViewModel
import com.example.rickmorty.ui.screens.episodes.EpisodeViewModel
import com.example.rickmorty.ui.screens.episodes.detail.EpisodesDetailViewModel
import com.example.rickmorty.ui.screens.locations.LocationViewModel
import com.example.rickmorty.ui.screens.locations.detail.LocationsDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { CharacterViewModel(get()) }
    viewModel { (characterId: Int) ->
        CharacterDetailViewModel(get(), characterId)
    }
    viewModel { EpisodeViewModel(get()) }
    viewModel { (episodeId: Int) ->
        EpisodesDetailViewModel(get(), episodeId)
    }

    viewModel { LocationViewModel(get()) }
    viewModel { (locationId: Int) ->
        LocationsDetailViewModel(get(), locationId)
    }
}