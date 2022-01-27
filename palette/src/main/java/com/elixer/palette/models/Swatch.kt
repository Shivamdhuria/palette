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
data class Swatch(
    val radius: Float,
    val strokeWidth: Float,
    val startingAngle: Float,
    val sweep: Float,
    val color: List<Color>,
    val spacerOutward: Float
)

fun Swatch.toColorArch(isSelected: Boolean): List<ColorArch> {
    val list = mutableListOf<ColorArch>()
    var startingRadius = radius
    this.color.forEach {
        list.add(
            ColorArch(
                radius = startingRadius,
                strokeWidth = strokeWidth,
                startingAngle = startingAngle,
                sweep = sweep,
                color = it,
                isSelected = isSelected
            )
        )
        startingRadius += strokeWidth + spacerOutward
    }
    return list
}
