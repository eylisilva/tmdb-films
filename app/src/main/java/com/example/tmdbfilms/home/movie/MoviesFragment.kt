package com.example.tmdbfilms.home.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.*
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {

    private lateinit var moviesRv: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeProviderMultiAdapter: HomeProviderMultiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesRv = view.findViewById(R.id.rv_movies)
        homeProviderMultiAdapter = HomeProviderMultiAdapter()
        moviesRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeProviderMultiAdapter
        }
        parentFragment?.let {
            homeViewModel = ViewModelProvider(it)[HomeViewModel::class.java]
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeUiStateFlow.distinctUntilChangedBy { it.moviesUiState }.collect {
                    val items = mutableListOf<ProviderMultiEntity>()
                    if (it.moviesUiState?.movieSliderItems != null) {
                        items.add(SliderMultiEntity(it.moviesUiState.movieSliderItems))
                    }
                    items.add(HeaderMultiEntity(getString(R.string.top_rated)))
                    homeProviderMultiAdapter.setList(items)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }
}