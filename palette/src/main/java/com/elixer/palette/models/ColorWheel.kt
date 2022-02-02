package com.elixer.palette.models

import androidx.compose.ui.graphics.Color

/**
 * Color wheel holds all the swatches from 0 to 360 degree extending outwards
 * @param startingRadius the starting radius of the inner color wheel
 * @param strokeWidth the stroke width of a colorArc outwards
 * @param isDisplayed value to show if colorWheel is displayed or not
 * @param spacerRotation gap between color swatches radially
 * @param spacerOutward gap between color Arcs radially outwards
 */
data class ColorWheel(
    val startingRadius: Float,
    val swatches: List<List<Color>>,
    val strokeWidth: Float,
    val isDisplayed: Boolean,
    val spacerRotation: Float = 0f,
    val spacerOutward: Float = 0f
)

fun ColorWheel.toSwatches(): List<Swatch> {
    var startAngle = 0f
    val degreeEach = (360f - (swatches.size * spacerRotation)) / (swatches.size)
    val swatchesList = mutableListOf<Swatch>()
    swatches.forEach {
        swatchesList.add(
            Swatch(
                radius = startingRadius,
                strokeWidth = strokeWidth,
                startingAngle = startAngle,
                sweep = degreeEach,
                spacerOutward = spacerOutward,
                colors = it
            )
        )
        startAngle += degreeEach + spacerRotation
    }
    return swatchesList
}
