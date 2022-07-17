package com.example.tmdbfilms.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

@Suppress("UNCHECKED_CAST")
class HomeViewModel(private val getHomePageDataUseCase: GetHomePageDataUseCase) : ViewModel() {

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
                        homeData.topRatedMovieItems
                    ),
                    tvShowsUiState = TvShowsUiState(
                        homeData.tvShowSliderItems,
                        homeData.topRatedTvShowItems
                    )
                )
            }
        }
    }

    data class HomeUiState(
        val loading: HomeLoadingState,
        val moviesUiState: MoviesUiState? = null,
        val tvShowsUiState: TvShowsUiState? = null
    )

    data class MoviesUiState(
        val movieSliderItems: List<SliderImageData>? = null,
        val topRatedMovieItems: List<CardData>? = null
    )

    data class TvShowsUiState(
        val tvSliderItems: List<SliderImageData>? = null,
        val topRatedTvShowItems: List<CardData>? = null
    )

    sealed class HomeLoadingState {

        object Init : HomeLoadingState()

        object Loading : HomeLoadingState()

        object Success : HomeLoadingState()

        object Error : HomeLoadingState()

    }

    class HomeViewModelFactory(private val getHomePageDataUseCase: GetHomePageDataUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(getHomePageDataUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}