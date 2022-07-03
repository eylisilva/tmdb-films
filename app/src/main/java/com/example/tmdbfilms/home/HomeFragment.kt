package com.example.tmdbfilms.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.tmdbfilms.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var homeVp: ViewPager2
    private lateinit var homeTl: TabLayout
    private lateinit var homePagerAdapter: HomePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}