package com.example.rickmorty.ui.screens.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickmorty.data.remote.dto.Episode
import com.example.rickmorty.data.repository.EpisodesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeViewModel(private val episodesRepository: EpisodesRepository) : ViewModel() {

    private val _episodeFlow = MutableSharedFlow<PagingData<Episode>>()
    val episodeFlow: SharedFlow<PagingData<Episode>> = _episodeFlow.asSharedFlow()

    fun fetchAllEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
                episodesRepository.fetchAllEpisodes()
                    .flow
                    .collectLatest { pagingData ->
                        _episodeFlow.emit(pagingData)
                    }
        }
    }
}