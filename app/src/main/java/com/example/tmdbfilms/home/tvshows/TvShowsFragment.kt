package com.example.tmdbfilms.home.tvshows

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
import com.example.tmdbfilms.home.HomeProviderMultiAdapter
import com.example.tmdbfilms.home.HomeViewModel
import com.example.tmdbfilms.home.ProviderMultiEntity
import com.example.tmdbfilms.home.SliderMultiEntity
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class TvShowsFragment : Fragment() {

    private lateinit var tvShowsRv: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeProviderMultiAdapter: HomeProviderMultiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowsRv = view.findViewById(R.id.rv_tvshows)
        homeProviderMultiAdapter = HomeProviderMultiAdapter()
        tvShowsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeProviderMultiAdapter
        }
        parentFragment?.let {
            homeViewModel = ViewModelProvider(it)[HomeViewModel::class.java]
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeUiStateFlow.distinctUntilChangedBy { it.tvShowsUiState }.collect {
                    val items = mutableListOf<ProviderMultiEntity>()
                    if (it.tvShowsUiState?.tvSliderItems != null) {
                        items.add(SliderMultiEntity(it.tvShowsUiState.tvSliderItems))
                    }
                    homeProviderMultiAdapter.setList(items)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TvShowsFragment()
    }
}