package com.example.tmdbfilms

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.tmdbfilms.home.HomeFragment
import com.example.tmdbfilms.search.SearchFragment
import com.example.tmdbfilms.watchlist.WatchListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var fragmentContainerView: FragmentContainerView? = null
    private var bottomNavView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainerView = findViewById(R.id.fragment_container_view)
        bottomNavView = findViewById(R.id.bottom_nav)
        bottomNavView?.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<HomeFragment>(R.id.fragment_container_view)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<HomeFragment>(R.id.fragment_container_view)
                }
            }
            R.id.nav_search -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<SearchFragment>(R.id.fragment_container_view)
                }
            }
            R.id.nav_watchlist -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<WatchListFragment>(R.id.fragment_container_view)
                }
            }
        }
        return true
    }


}