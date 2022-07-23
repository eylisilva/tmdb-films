package com.example.tmdbfilms.search

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbfilms.R
import com.example.tmdbfilms.UscFilmsApplication
import com.example.tmdbfilms.utils.ViewUtils
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var clearSearchIv: ImageView
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchResultRv: RecyclerView
    private lateinit var adapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val container = (activity?.application as UscFilmsApplication).container
        searchViewModel = ViewModelProvider(
            this,
            SearchViewModel.SearchViewModelFactory(container.searchRepository)
        )[SearchViewModel::class.java]
        searchView = view.findViewById(R.id.view_search)
        searchView.maxWidth = Int.MAX_VALUE
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        activity?.let {
            searchIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    it,
                    R.drawable.ic_baseline_search_24
                )
            )
        }
        searchView.apply {
            setIconifiedByDefault(false)
            requestFocus()
        }
        clearSearchIv = view.findViewById(R.id.iv_clear_search)
        clearSearchIv.setOnClickListener {
            searchView.setQuery("", false)
            searchViewModel.onQueryTextChange("")
        }
        searchResultRv = view.findViewById(R.id.rv_search_results)
        searchResultRv.layoutManager = LinearLayoutManager(context)
        searchResultRv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildLayoutPosition(view)
                if (position > 0) {
                    context?.let {
                        outRect.top = ViewUtils.dp2px(it, 10)
                    }
                }
            }
        })
        adapter = SearchResultAdapter()
        adapter.setEmptyView(R.layout.view_search_empty)
        adapter.setFooterView(
            LayoutInflater.from(context).inflate(R.layout.footer_search, searchResultRv, false)
        )
        searchResultRv.adapter = adapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null) {
                    return true
                }
                searchViewModel.onQueryTextChange(newText)
                return true
            }

        })
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.searchUiStateFlow.distinctUntilChangedBy { it.loadingState }.collect {
                    when (it.loadingState) {
                        SearchViewModel.SearchLoadingState.Empty, SearchViewModel.SearchLoadingState.Error -> {
                            clearSearchIv.isGone = true
                            searchResultRv.isGone = true
                        }
                        SearchViewModel.SearchLoadingState.Loading -> {
                            clearSearchIv.isVisible = true
                        }
                        SearchViewModel.SearchLoadingState.Success -> {
                            clearSearchIv.isVisible = true
                            searchResultRv.isVisible = true
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.searchUiStateFlow.distinctUntilChangedBy { it.searchResults }.collect {
                    adapter.setList(it.searchResults)
                }
            }
        }

    }

}