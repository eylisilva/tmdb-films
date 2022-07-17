package com.example.tmdbfilms.home

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class SliderItemProvider: BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = SLIDER
    override val layoutId: Int
        get() = R.layout.view_image_slider

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val sliderView = helper.getView<SliderView>(R.id.slider_view)
        val items = (item as SliderMultiEntity).items
        val adapter = HomeSliderAdapter()
        adapter.setNewData(items)
        sliderView.apply {
            setSliderAdapter(adapter)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            startAutoCycle()
        }
    }

}