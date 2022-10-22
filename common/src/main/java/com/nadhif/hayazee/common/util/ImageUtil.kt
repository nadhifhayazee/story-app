package com.nadhif.hayazee.common.util

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageUtil {

    fun getBitmapFromUrl(url: String?, context: Context): Bitmap {
        val option = RequestOptions()
            .fitCenter()
        return Glide.with(context.applicationContext)
            .setDefaultRequestOptions(option)
            .asBitmap()
            .load(url)
            .submit()
            .get()
    }
}