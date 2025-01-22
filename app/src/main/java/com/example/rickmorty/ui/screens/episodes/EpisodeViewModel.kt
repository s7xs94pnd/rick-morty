package com.example.rickmorty.ui.screens.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.dto.Episode
import com.example.rickmorty.data.repository.EpisodesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EpisodeViewModel(private val episodesRepository: EpisodesRepository) : ViewModel() {
    // TODO:    MutableStateFlow<List<Episode>>(emptyList())
    private val _episodeState = MutableStateFlow<List<Episode>>(emptyList())
    val episodesState: StateFlow<List<Episode>> = _episodeState.asStateFlow()

    // TODO: init for wt?
    init {
        fetchAllEpisodes()
    }

    private fun fetchAllEpisodes(){
        // TODO: viewModelScope.launch {  }
        viewModelScope.launch {
            val episodes = episodesRepository.fetchAllEpisodes()
            if (episodes != null){
                _episodeState.value =  episodes
            }
        }
    }
}