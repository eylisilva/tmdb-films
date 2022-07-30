package com.example.tmdbfilms.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class WatchListViewModel(private val watchListRepository: WatchListRepository) : ViewModel() {

    private val _watchListUiState = MutableStateFlow(WatchListUiState())

    val watchListUiState: StateFlow<WatchListUiState> = _watchListUiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            val items = watchListRepository.getAllWatchListItems()
            val itemUiStates = mutableListOf<WatchListItemUiState>()
            items.forEach {
                itemUiStates.add(
                    WatchListItemUiState(
                        id = it.id,
                        title = it.title,
                        posterPath = it.posterPath,
                        mediaType = it.mediaType,
                        onRemoveClick = {
                            onRemoveClick(it.id, it.title)
                        })
                )
            }
            _watchListUiState.update {
                it.copy(
                    items = itemUiStates
                )
            }
        }
    }

    fun onRowMoved(fromPosition: Int, toPosition: Int) {
        viewModelScope.launch {
            watchListRepository.move(fromPosition, toPosition)
        }
    }

    fun userMessageShown() {
        _watchListUiState.update {
            it.copy(
                toastMessage = null
            )
        }
    }

    private fun onRemoveClick(id: Int, title: String) {
        viewModelScope.launch {
            watchListRepository.removeFromWatchList(id)
            loadData()
            _watchListUiState.update {
                it.copy(
                    toastMessage = "$title was removed from Watchlist"
                )
            }
        }
    }

    data class WatchListUiState(
        val items: List<WatchListItemUiState> = mutableListOf(),
        val toastMessage: String? = null
    )

    class WatchListViewModelFactory(private val watchListRepository: WatchListRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WatchListViewModel::class.java)) {
                return WatchListViewModel(watchListRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}