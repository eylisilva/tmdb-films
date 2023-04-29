package com.example.tmdbfilms.detail.review

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbfilms.BaseActivity
import com.example.tmdbfilms.R
import com.example.tmdbfilms.detail.DetailSubtitleMultiEntity
import com.example.tmdbfilms.detail.DetailTextMultiEntity
import com.example.tmdbfilms.home.ProviderMultiEntity

class ReviewsActivity : BaseActivity() {

    private var reviewsRv: RecyclerView? = null
    private val adapter by lazy {
        ReviewsProviderMultiAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)
        reviewsRv = findViewById(R.id.rv_reviews)
        reviewsRv?.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        reviewsRv?.adapter = adapter
        val items = mutableListOf<ProviderMultiEntity>()
        val rating = intent.getStringExtra("rating") ?: "0/5"
        items.add(ReviewsRatingMultiEntity(rating))
        val title = intent.getStringExtra("title") ?: ""
        items.add(DetailSubtitleMultiEntity(title))
        val content = intent.getStringExtra("content") ?: ""
        items.add(DetailTextMultiEntity(content, Int.MAX_VALUE))
        adapter.setList(items)
        adapter.setFooterView(
            LayoutInflater.from(this).inflate(R.layout.footer_search, reviewsRv, false)
        )
    }

}