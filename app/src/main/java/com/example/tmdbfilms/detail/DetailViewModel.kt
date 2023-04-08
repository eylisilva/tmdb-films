package com.example.tmdbfilms.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tmdbfilms.UscFilmsApplication
import com.example.tmdbfilms.detail.actor.ActorData
import com.example.tmdbfilms.detail.review.ReviewData
import com.example.tmdbfilms.home.AddToWatchListUseCase
import com.example.tmdbfilms.home.RemoveFromWatchListUseCase
import com.example.tmdbfilms.watchlist.WatchListItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "DetailViewModel"

data class DetailUiState(
    val trailerVideoKey: String = "",
    val backdropPath: String = "",
    val name: String = "",
    val overview: String = "",
    val genres: String = "",
    val year: String = "",
    val addedToWatchList: Boolean = false,
    val onAddToWatchList: () -> Unit = {},
    val onRemoveFromWatchList: () -> Unit = {},
    val toastMessage: String? = null,
    val cast: List<ActorData> = listOf(),
    val reviews: List<ReviewData> = listOf()
)

@Suppress("UNCHECKED_CAST")
class DetailViewModel(
    private val getDetailPageDataUseCase: GetDetailPageDataUseCase,
    private val addToWatchListUseCase: AddToWatchListUseCase,
    private val removeFromWatchListUseCase: RemoveFromWatchListUseCase
) :
    ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as UscFilmsApplication).container
                DetailViewModel(
                    getDetailPageDataUseCase = GetDetailPageDataUseCase(
                        videoRepository = container.videoRepository,
                        detailRepository = container.detailRepository,
                        watchListRepository = container.watchListRepository,
                        reviewRepository = container.reviewRepository,
                        moviesRepository = container.moviesRepository,
                        tvShowsRepository = container.tvShowsRepository,
                        actorRepository = container.actorRepository
                    ),
                    addToWatchListUseCase = AddToWatchListUseCase(container.watchListRepository),
                    removeFromWatchListUseCase = RemoveFromWatchListUseCase(container.watchListRepository)
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getDetailPageData(id: Int, type: Int) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "exception: $exception")
        }) {
            val detailPageData = getDetailPageDataUseCase.getDetailPageData(id, type)
            _uiState.update { detailUiState ->
                detailUiState.copy(
                    trailerVideoKey = detailPageData.videoKey ?: "",
                    backdropPath = detailPageData.detailData.backdropPath,
                    name = detailPageData.detailData.title,
                    overview = detailPageData.detailData.overview,
                    genres = detailPageData.detailData.genres,
                    year = detailPageData.detailData.year,
                    addedToWatchList = detailPageData.addedToWatchList,
                    onAddToWatchList = {
                        viewModelScope.launch {
                            addToWatchListUseCase.addToWatchList(
                                WatchListItem(
                                    id = id,
                                    title = detailPageData.detailData.title,
                                    posterPath = detailPageData.detailData.backdropPath,
                                    mediaType = when (type) {
                                        1 -> "movie"
                                        2 -> "tv"
                                        else -> "movie"
                                    }
                                )
                            )
                            _uiState.update {
                                it.copy(
                                    addedToWatchList = true,
                                    toastMessage = "${detailPageData.detailData.title} was added to Watchlist"
                                )
                            }
                        }
                    },
                    onRemoveFromWatchList = {
                        viewModelScope.launch {
                            removeFromWatchListUseCase.removeFromWatchList(id)
                        }
                        _uiState.update {
                            it.copy(
                                addedToWatchList = false,
                                toastMessage = "${detailPageData.detailData.title} was removed from Watchlist"
                            )
                        }
                    },
                    cast = detailPageData.actors,
                    reviews = detailPageData.reviews
                )
            }
        }
    }

    fun toastMessageShown() {
        _uiState.update {
            it.copy(toastMessage = null)
        }
    }

}