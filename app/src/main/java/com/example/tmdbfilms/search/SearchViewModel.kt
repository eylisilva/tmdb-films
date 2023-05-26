package com.example.tmdbfilms.search

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

private const val TAG = "SearchViewModel"

@Suppress("UNCHECKED_CAST")
class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _searchUiStateFlow =
        MutableStateFlow(SearchUiState(loadingState = SearchLoadingState.Empty))
    val searchUiStateFlow: StateFlow<SearchUiState> = _searchUiStateFlow.asStateFlow()

    fun onQueryTextChange(query: String) {
        if (query.isEmpty()) {
            _searchUiStateFlow.update {
                it.copy(
                    loadingState = SearchLoadingState.Empty,
                )
            }
            return
        }
        _searchUiStateFlow.update {
            it.copy(
                loadingState = SearchLoadingState.Loading,
            )
        }
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "exception: $exception")
            _searchUiStateFlow.update {
                it.copy(
                    loadingState = SearchLoadingState.Error,
                )
            }
        }) {
            val searchResults = searchRepository.multiSearch(query)
            _searchUiStateFlow.update {
                it.copy(
                    loadingState = SearchLoadingState.Success,
                    searchResults = searchResults
                )
            }
        }
    }

    data class SearchUiState(
        val loadingState: SearchLoadingState,
        val searchResults: List<SearchData>? = null
    )

    sealed class SearchLoadingState {

        object Empty : SearchLoadingState()

        object Loading : SearchLoadingState()

        object Success : SearchLoadingState()

        object Error : SearchLoadingState()

    }

    class SearchViewModelFactory(private val searchRepository: SearchRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(searchRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}