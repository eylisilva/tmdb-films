package com.example.tmdbfilms.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.tmdbfilms.R
import com.example.tmdbfilms.UscFilmsApplication
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var homeVp: ViewPager2
    private lateinit var homeTl: TabLayout
    private lateinit var homePagerAdapter: HomePagerAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var loadingGroup: Group
    private lateinit var loadingView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePagerAdapter = HomePagerAdapter(this)
        homeVp = view.findViewById(R.id.vp_home)
        homeVp.adapter = homePagerAdapter
        homeTl = view.findViewById(R.id.tl_home)
        TabLayoutMediator(homeTl, homeVp) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.movies)
                1 -> getString(R.string.tv_shows)
                else -> throw IllegalStateException("wrong number of fragments")
            }
        }.attach()
        val container = (activity?.application as UscFilmsApplication).container
        homeViewModel =
            ViewModelProvider(
                this,
                HomeViewModel.HomeViewModelFactory(
                    GetHomePageDataUseCase(
                        container.moviesRepository,
                        container.tvShowsRepository
                    )
                )
            )[HomeViewModel::class.java]
        loadingGroup = view.findViewById(R.id.group_loading)
        loadingView = view.findViewById(R.id.view_loading)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeUiStateFlow.distinctUntilChangedBy { it.loading }.collect {
                    when (it.loading) {
                        HomeViewModel.HomeLoadingState.Loading -> {
                            loadingView.isVisible = true
                            loadingGroup.isVisible = true
                        }
                        HomeViewModel.HomeLoadingState.Success -> {
                            loadingView.isGone = true
                            loadingGroup.isGone = true
                        }
                        HomeViewModel.HomeLoadingState.Init, HomeViewModel.HomeLoadingState.Error -> {
                            loadingView.isVisible = true
                            loadingGroup.isGone = true
                        }
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchHomeData()
    }

}