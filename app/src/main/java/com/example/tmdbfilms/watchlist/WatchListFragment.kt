package com.example.tmdbfilms.watchlist

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbfilms.R
import com.example.tmdbfilms.UscFilmsApplication
import com.example.tmdbfilms.utils.ViewUtils
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class WatchListFragment : Fragment() {

    private lateinit var watchListViewModel: WatchListViewModel
    private lateinit var watchListRv: RecyclerView
    private lateinit var adapter: WatchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watch_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val container = (activity?.application as UscFilmsApplication).container
        watchListViewModel = ViewModelProvider(
            this,
            WatchListViewModel.WatchListViewModelFactory(container.watchListRepository)
        )[WatchListViewModel::class.java]
        watchListRv = view.findViewById(R.id.rv_watch_list)
        watchListRv.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = ViewUtils.dp2px(context, 20)
                    outRect.left = ViewUtils.dp2px(context, 10)
                }
            })
        }
        adapter = WatchListAdapter(onRowMovedCallback = { fromPosition, toPosition ->
            watchListViewModel.onRowMoved(fromPosition, toPosition)
        })
        adapter.setEmptyView(R.layout.view_watch_list_empty)
        watchListRv.adapter = adapter
        val callback = ItemMoveCallback(adapter)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(watchListRv)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                watchListViewModel.watchListUiState.distinctUntilChangedBy { it.items }.collect {
                    it.items.let { list ->
                        adapter.setList(list)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                watchListViewModel.watchListUiState.distinctUntilChangedBy { it.toastMessage }.collect {
                    if (it.toastMessage != null) {
                        Toast.makeText(
                            context?.applicationContext,
                            it.toastMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                        watchListViewModel.userMessageShown()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        watchListViewModel.loadData()
    }

}