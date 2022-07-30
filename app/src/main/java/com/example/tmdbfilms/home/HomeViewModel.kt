package com.example.tmdbfilms.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tmdbfilms.watchlist.WatchListItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

@Suppress("UNCHECKED_CAST")
class HomeViewModel(
    private val getHomePageDataUseCase: GetHomePageDataUseCase,
    private val isContainedInWatchListUseCase: IsContainedInWatchListUseCase,
    private val addToWatchListUseCase: AddToWatchListUseCase,
    private val removeFromWatchListUseCase: RemoveFromWatchListUseCase
) : ViewModel() {

    private val _homeUiStateFlow = MutableStateFlow(
        HomeUiState(
            loading = HomeLoadingState.Init,
        )
    )
    val homeUiStateFlow: StateFlow<HomeUiState> = _homeUiStateFlow.asStateFlow()

    fun fetchHomeData() {
        _homeUiStateFlow.update {
            it.copy(loading = HomeLoadingState.Loading)
        }
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "exception: $exception")
            _homeUiStateFlow.update {
                it.copy(
                    loading = HomeLoadingState.Error,
                )
            }
        }) {
            val homeData = getHomePageDataUseCase.getHomePageData()
            _homeUiStateFlow.update {
                it.copy(
                    loading = HomeLoadingState.Success,
                    moviesUiState = MoviesUiState(
                        homeData.movieSliderItems,
                        convertToUiState(homeData.topRatedMovieItems),
                        convertToUiState(homeData.popularMovieItems)
                    ),
                    tvShowsUiState = TvShowsUiState(
                        homeData.tvShowSliderItems,
                        convertToUiState(homeData.topRatedTvShowItems),
                        convertToUiState(homeData.popularTvShowItems)
                    )
                )
            }
        }
    }

    fun userToastShown() {
        _homeUiStateFlow.update {
            it.copy(toastMessage = null)
        }
    }

    private suspend fun convertToUiState(list: List<CardData>?): List<CardUiState> {
        val results = mutableListOf<CardUiState>()
        list?.forEach {
            val addedToWatchList = isContainedInWatchListUseCase.isContainedInWatchList(it.id)
            results.add(
                CardUiState(
                    id = it.id,
                    posterPath = it.posterPath,
                    type = it.type,
                    addedToWatchList = addedToWatchList,
                    onAddToWatchList = {
                        viewModelScope.launch {
                            addToWatchListUseCase.addToWatchList(
                                WatchListItem(
                                    it.id,
                                    it.title,
                                    it.posterPath,
                                    it.type
                                )
                            )
                            _homeUiStateFlow.update { state ->
                                state.copy(toastMessage = "${it.title} was added to Watchlist")
                            }
                        }
                    },
                    onRemoveFromWatchList = {
                        viewModelScope.launch {
                            removeFromWatchListUseCase.removeFromWatchList(it.id)
                            _homeUiStateFlow.update { state ->
                                state.copy(toastMessage = "${it.title} was removed from Watchlist")
                            }
                        }
                    }
                )
            )
        }
        return results
    }

    data class HomeUiState(
        val loading: HomeLoadingState,
        val moviesUiState: MoviesUiState? = null,
        val tvShowsUiState: TvShowsUiState? = null,
        val toastMessage: String? = null
    )

    data class MoviesUiState(
        val movieSliderItems: List<SliderImageData>? = null,
        val topRatedMovieItems: List<CardUiState>? = null,
        val popularMovieItems: List<CardUiState>? = null
    )

    data class TvShowsUiState(
        val tvSliderItems: List<SliderImageData>? = null,
        val topRatedTvShowItems: List<CardUiState>? = null,
        val popularTvShowItems: List<CardUiState>? = null
    )

    sealed class HomeLoadingState {

        object Init : HomeLoadingState()

        object Loading : HomeLoadingState()

        object Success : HomeLoadingState()

        object Error : HomeLoadingState()

    }

    class HomeViewModelFactory(
        private val getHomePageDataUseCase: GetHomePageDataUseCase,
        private val isContainedInWatchListUseCase: IsContainedInWatchListUseCase,
        private val addToWatchListUseCase: AddToWatchListUseCase,
        private val removeFromWatchListUseCase: RemoveFromWatchListUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(
                    getHomePageDataUseCase,
                    isContainedInWatchListUseCase,
                    addToWatchListUseCase,
                    removeFromWatchListUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}