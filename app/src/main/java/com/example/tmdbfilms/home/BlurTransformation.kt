@file:Suppress("DEPRECATION")

package com.example.tmdbfilms.home

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import coil.size.Size
import coil.transform.Transformation

private const val BLURRED_RADIUS = 20f

class BlurTransformation(context: Context) : Transformation {

    private val rs: RenderScript

    init {
        rs = RenderScript.create(context)
    }

    override val cacheKey: String
        get() = javaClass.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val blurredBitmap = input.copy(Bitmap.Config.ARGB_8888, true)
        val newInput = Allocation.createFromBitmap(
            rs,
            blurredBitmap,
            Allocation.MipmapControl.MIPMAP_FULL,
            Allocation.USAGE_SHARED
        )
        val output = Allocation.createTyped(rs, newInput.type)
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.apply {
            setInput(newInput)
            setRadius(BLURRED_RADIUS)
            forEach(output)
        }
        output.copyTo(blurredBitmap)
        return blurredBitmap
    }

}