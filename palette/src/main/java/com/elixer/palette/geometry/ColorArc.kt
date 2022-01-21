package com.elixer.palette.geometry

import android.util.Log
import androidx.compose.ui.graphics.Color

data class ColorArc(

    val startingAngle: Float,

    val sweep: Float,

    val color: Color,

    val innerRadius: Float,

    val strokeWidth: Float

) {

    fun contains(angle: Float, distance: Float): Boolean {
        if (angle in startingAngle.rangeTo(startingAngle + sweep)) {
            Log.e("angle true", "${startingAngle}..$angle.. ${startingAngle+sweep}")
            Log.e("distance idk", "${innerRadius}..$distance...${innerRadius+strokeWidth}")
            return distance in innerRadius..(innerRadius + strokeWidth)
        } else return false
    }
}

