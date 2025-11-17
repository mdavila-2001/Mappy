package com.mdavila_2001.mappyapp.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object Tools {
    fun bitmapDescriptorFromVector(
        context: Context,
        vectorResId: Int,
        width: Int? = null,
        height: Int? = null
    ): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId) ?: return null

        val bitmapWidth = width ?: vectorDrawable.intrinsicWidth
        val bitmapHeight = height ?: vectorDrawable.intrinsicHeight

        vectorDrawable.setBounds(0, 0, bitmapWidth, bitmapHeight)
        val bitmap = Bitmap.createBitmap(
            bitmapWidth,
            bitmapHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}