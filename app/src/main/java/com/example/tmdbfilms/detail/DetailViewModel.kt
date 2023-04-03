package com.example.tmdbfilms.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tmdbfilms.AppContainer
import com.example.tmdbfilms.UscFilmsApplication
import com.example.tmdbfilms.search.SearchViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "DetailViewModel"

data class DetailUiState(
    val trailerVideoKey: String = "",
    val backdropPath: String = "",
    val name: String = "",
    val overview: String = "",
    val genres: String = "",
    val year: String = ""
)

@Suppress("UNCHECKED_CAST")
class DetailViewModel(private val getDetailPageDataUseCase: GetDetailPageDataUseCase) :
    ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as UscFilmsApplication).container
                DetailViewModel(getDetailPageDataUseCase = GetDetailPageDataUseCase(
                    videoRepository = container.videoRepository,
                    detailRepository = container.detailRepository,
                    watchListRepository = container.watchListRepository,
                    reviewRepository = container.reviewRepository,
                    moviesRepository = container.moviesRepository,
                    tvShowsRepository = container.tvShowsRepository
                ))
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
            _uiState.update {
                it.copy(
                    trailerVideoKey = detailPageData.videoKey ?: "",
                    backdropPath = detailPageData.detailData.backdropPath,
                    name = detailPageData.detailData.title,
                    overview = detailPageData.detailData.overview,
                    genres = detailPageData.detailData.genres,
                    year = detailPageData.detailData.year
                )
            }
        }
    }

}