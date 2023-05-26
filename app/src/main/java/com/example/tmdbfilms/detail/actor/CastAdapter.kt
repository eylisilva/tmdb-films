package com.example.tmdbfilms.detail.actor

import android.widget.TextView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import de.hdodenhof.circleimageview.CircleImageView

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class CastAdapter : BaseQuickAdapter<ActorData, BaseViewHolder>(R.layout.item_detail_actor){

    override fun convert(holder: BaseViewHolder, item: ActorData) {
        val actorIv = holder.getView<CircleImageView>(R.id.iv_actor)
        actorIv.load("${IMAGE_BASE_URL}${item.profilePath}")
        val actorNameTv = holder.getView<TextView>(R.id.tv_actor_name)
        actorNameTv.text = item.name
    }

}