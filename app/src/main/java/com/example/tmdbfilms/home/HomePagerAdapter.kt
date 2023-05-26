package com.example.tmdbfilms.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdbfilms.home.movie.MoviesFragment
import com.example.tmdbfilms.home.tvshows.TvShowsFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment.newInstance()
            1 -> TvShowsFragment.newInstance()
            else -> throw IllegalStateException("wrong number of fragments")
        }
    }

}