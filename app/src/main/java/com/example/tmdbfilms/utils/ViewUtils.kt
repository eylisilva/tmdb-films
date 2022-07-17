package com.example.tmdbfilms.utils

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.round

object ViewUtils {
    fun dp2px(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}