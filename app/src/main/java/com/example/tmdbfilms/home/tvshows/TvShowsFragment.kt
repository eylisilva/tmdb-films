package com.example.tmdbfilms.home.tvshows

import android.graphics.Rect
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
import com.example.tmdbfilms.utils.ViewUtils
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

private const val ITEM_MARGIN = 16

class TvShowsFragment : Fragment() {

    private lateinit var tvShowsRv: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeProviderMultiAdapter: HomeProviderMultiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowsRv = view.findViewById(R.id.rv_tvshows)
        homeProviderMultiAdapter = HomeProviderMultiAdapter()
        tvShowsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeProviderMultiAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.bottom = ViewUtils.dp2px(context, ITEM_MARGIN)
                }
            })
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
                    if (it.tvShowsUiState?.topRatedTvShowItems != null) {
                        items.add(HeaderMultiEntity(getString(R.string.top_rated)))
                        items.add(HorizontalScrollMultiEntity(it.tvShowsUiState.topRatedTvShowItems))
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