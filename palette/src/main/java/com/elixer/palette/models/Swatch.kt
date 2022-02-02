package com.elixer.palette.models

import androidx.compose.ui.graphics.Color

/**
 * Swatch holds all the color Arc shades only extending outwards
 * @param radius the starting radius of the first color Arc
 * @param strokeWidth the stroke width of a colorArc outwards
 * @param startingAngle the angle to start the arc from 0 - 360
 * @param sweep arc drawn from starting -> starting + sweep
 * @param colors all the shades of a colour to be represented in the swatch
 * @param spacerOutward gap between color Arcs radially outwards
 */
data class Swatch(
    val radius: Float,
    val strokeWidth: Float,
    val startingAngle: Float,
    val sweep: Float,
    val colors: List<Color>,
    val spacerOutward: Float
)

fun Swatch.toColorArch(): List<ColorArc> {
    val list = mutableListOf<ColorArc>()
    var startingRadius = radius
    this.colors.forEach {
        list.add(
            ColorArc(
                radius = startingRadius,
                strokeWidth = strokeWidth,
                startingAngle = startingAngle,
                sweep = sweep,
                color = it,
            )
        )
        startingRadius += strokeWidth + spacerOutward
    }
    return list
}
