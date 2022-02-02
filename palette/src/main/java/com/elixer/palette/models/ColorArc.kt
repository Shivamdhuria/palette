package com.elixer.palette.models

import androidx.compose.ui.graphics.Color

/**
 * Color Arc holding exact data to instruct where and how to draw a an arc
 * @param radius radius if the arc
 * @param strokeWidth stroke width directed radially outwards
 * @param startingAngle the angle to start the arc from 0 - 360
 * @param sweep arc drawn from starting -> starting + sweep
 * @param color the color that will be used to paint the arc
 */
data class ColorArc(
    val radius: Float,
    val strokeWidth: Float,
    val startingAngle: Float,
    val sweep: Float,
    val color: Color,
) {

    fun contains(angle: Float, distance: Float, rotation: Float): Boolean {
        if (angle in ((startingAngle + rotation) % 360).rangeTo((startingAngle + sweep + rotation) % 360f)) {
            return distance in (radius - strokeWidth)..(radius + strokeWidth)
        } else return false
    }
}