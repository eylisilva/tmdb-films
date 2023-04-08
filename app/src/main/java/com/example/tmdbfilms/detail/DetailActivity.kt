package com.example.tmdbfilms.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbfilms.BaseActivity
import com.example.tmdbfilms.R
import com.example.tmdbfilms.detail.actor.DetailCastMultiEntity
import com.example.tmdbfilms.detail.name.DetailNameMultiEntity
import com.example.tmdbfilms.detail.overview.DetailOverviewMultiEntity
import com.example.tmdbfilms.detail.shareandwatchlist.DetailShareAndWatchListItemMultiEntity
import com.example.tmdbfilms.detail.video.PlaceHolderBackdropMultiEntity
import com.example.tmdbfilms.detail.video.TrailerMultiEntity
import com.example.tmdbfilms.home.ProviderMultiEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class DetailActivity : BaseActivity() {

    private val detailViewModel: DetailViewModel by viewModels { DetailViewModel.Factory }
    private var detailRv: RecyclerView? = null
    private val detailAdapter: DetailProviderMultiAdapter by lazy {
        DetailProviderMultiAdapter(lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailRv = findViewById(R.id.rv_detail)
        detailRv?.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        detailRv?.adapter = detailAdapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.uiState.collect {
                    val items = mutableListOf<ProviderMultiEntity>()
                    if (it.trailerVideoKey.isNotEmpty()) {
                        items.add(TrailerMultiEntity(it.trailerVideoKey))
                    } else {
                        items.add(PlaceHolderBackdropMultiEntity(it.backdropPath))
                    }
                    items.add(DetailNameMultiEntity(it.name))
                    items.add(DetailSubtitleMultiEntity(getString(R.string.overview)))
                    items.add(DetailOverviewMultiEntity(it.overview))
                    items.add(DetailSubtitleMultiEntity(getString(R.string.genres)))
                    items.add(DetailTextMultiEntity(it.genres))
                    items.add(DetailSubtitleMultiEntity(getString(R.string.year)))
                    items.add(DetailTextMultiEntity(it.year))
                    items.add(
                        DetailShareAndWatchListItemMultiEntity(
                            id = intent.getIntExtra("id", 0),
                            type = when (intent.getIntExtra("media_type", 0)) {
                                1 -> "movie"
                                2 -> "tv"
                                else -> "movie"
                            },
                            addedToWatchList = it.addedToWatchList,
                            onAddToWatchList = it.onAddToWatchList,
                            onRemoveFromWatchList = it.onRemoveFromWatchList
                        )
                    )
                    if (it.cast.isNotEmpty()) {
                        items.add(DetailSubtitleMultiEntity(getString(R.string.cast)))
                        items.add(DetailCastMultiEntity(it.cast))
                    }
                    detailAdapter.setList(items)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.uiState.distinctUntilChangedBy { it.toastMessage }.collect {
                    if (it.toastMessage != null) {
                        Toast.makeText(
                            applicationContext,
                            it.toastMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                        detailViewModel.toastMessageShown()
                    }
                }
            }
        }
        val id = intent.getIntExtra("id", 0)
        val mediaType = intent.getIntExtra("media_type", 0)
        detailViewModel.getDetailPageData(id, mediaType)
    }

}