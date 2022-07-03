package com.example.tmdbfilms.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class HomeViewModel(private val getHomePageDataUseCase: GetHomePageDataUseCase) : ViewModel() {

    private val _homeUiStateFlow = MutableStateFlow(
        HomeUiState(
            loading = false,
            success = false
        )
    )
    val homeUiStateFlow: StateFlow<HomeUiState> = _homeUiStateFlow.asStateFlow()

    fun fetchHomeData() {
        _homeUiStateFlow.update {
            it.copy(loading = true)
        }
        viewModelScope.launch {
            val homeData = getHomePageDataUseCase.getHomePageData()
            if (homeData.success) {
                _homeUiStateFlow.update {
                    it.copy(
                        loading = false,
                        success = true,
                        movieSliderItems = homeData.movieSliderItems,
                        tvShowSliderItems = homeData.tvShowSliderItems
                    )
                }
            } else {
                _homeUiStateFlow.update {
                    it.copy(
                        loading = false,
                        success = false
                    )
                }
            }
        }
    }

    data class HomeUiState(
        val loading: Boolean,
        val success: Boolean,
        val movieSliderItems: List<SliderImageItem>? = null,
        val tvShowSliderItems: List<SliderImageItem>? = null
    )

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