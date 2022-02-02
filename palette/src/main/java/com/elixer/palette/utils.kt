package com.elixer.palette

import kotlin.math.atan2
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

class Utils {

    companion object {
        fun calculateAngle(centerX: Float, centerY: Float, x2: Float, y2: Float): Float {
            var angle = Math.toDegrees(atan2(y2 - centerY, x2 - centerX).toDouble())
            angle += ceil(-angle / 360) * 360
            return angle.toFloat()
        }

        fun calculateDistance(centerX: Float, centerY: Float, x2: Float, y2: Float): Float {
            return sqrt((x2 - centerX).toDouble().pow(2.0) + (y2 - centerY).toDouble().pow(2.0)).toFloat()
        }
    }
}