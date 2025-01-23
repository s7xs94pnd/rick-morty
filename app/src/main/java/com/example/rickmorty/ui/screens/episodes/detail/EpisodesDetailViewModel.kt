package com.example.rickmorty.ui.screens.episodes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.remote.dto.Episode
import com.example.rickmorty.data.repository.EpisodesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EpisodesDetailViewModel(
    private val episodesRepository: EpisodesRepository,
    private val episodeId: Int
):ViewModel() {

    private val _episodeState = MutableStateFlow<Episode?>(null)
    val episodeState = _episodeState.asStateFlow()

     fun fetchEpisodeById() {
        viewModelScope.launch(Dispatchers.IO) {
            val episode = episodesRepository.fetchEpisodesById(episodeId)
            if (episode!=null){
                _episodeState.value = episode
            }
        }
    }
}