package com.elixer.palette.models

import androidx.compose.ui.graphics.Color

/**
 * Particle holding exact data to instruct where and how to draw a particle
 * @param x the absolute x position on the canvas
 * @param y the absolute y position on the canvas
 * @param width the current width of the confetti
 * @param height the current height of a confetti
 * @param color the color that will be used to paint the confetti
 * @param rotation the current rotation of the confetti in degrees
 * @param scaleX the current scale of the confetti used to create a 3D rotation
 * @param shape the Shape of the confetti such as a circle, square of custom shape
 * @param alpha the transparency of the confetti between 0 - 255
 */
data class ColorWheel(
    val radius: Float,
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
                radius = radius,
                strokeWidth = strokeWidth,
                startingAngle = startAngle,
                sweep = degreeEach,
                spacerOutward = spacerOutward,
                color = it
            )
        )
        startAngle += degreeEach + spacerOutward
    }
    return swatchesList
}
