package com.example.tmdbfilms.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import coil.load
import com.example.tmdbfilms.R
import com.smarteist.autoimageslider.SliderViewAdapter

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class HomeSliderAdapter : SliderViewAdapter<HomeSliderAdapter.HomeSliderViewHolder>() {

    private var items = listOf<SliderImageData>()

    override fun getCount(): Int = items.size

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup?): HomeSliderViewHolder {
        val inflate = LayoutInflater.from(parent?.context).inflate(R.layout.item_image_slider, null)
        return HomeSliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: HomeSliderViewHolder?, position: Int) {
        val item = items[position]
        viewHolder?.sliderIv?.load("${IMAGE_BASE_URL}${item.posterPath}")
        viewHolder?.sliderBlurredIv?.load("${IMAGE_BASE_URL}${item.posterPath}") {
            transformations(BlurTransformation(viewHolder.sliderBlurredIv.context))
        }
    }

    fun setNewData(data: List<SliderImageData>) {
        items = data
        notifyDataSetChanged()
    }

    class HomeSliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        val sliderIv: ImageView
        val sliderBlurredIv: ImageView

        init {
            sliderIv = itemView.findViewById(R.id.iv_slider)
            sliderBlurredIv = itemView.findViewById(R.id.iv_slider_blurred)
        }
    }

}