package com.elixer.palette.models

import android.util.Log
import androidx.compose.ui.graphics.Color

data class ColorArch(
    val radius: Float,
    val strokeWidth: Float,
    val startingAngle: Float,
    val sweep: Float,
    val color: Color,
    val isSelected: Boolean
) {

    fun contains(angle: Float, distance: Float, rotation: Float): Boolean {

        if (angle in ((startingAngle + rotation) % 360).rangeTo((startingAngle + sweep + rotation) % 360f)) {
            Log.e("angle true", "${startingAngle}..$angle.. ${startingAngle + sweep}")
            Log.e("distance idk", "${radius}..$distance...${radius + strokeWidth}")
            return distance in (radius - strokeWidth)..(radius + strokeWidth)
        } else return false
    }
}
