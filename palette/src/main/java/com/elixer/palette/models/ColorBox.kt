package com.elixer.palette.models

import androidx.compose.ui.graphics.Color

/**
 * Particle holding exact data to instruct where and how to draw a particle
 * @param x the absolute x position on the canvas
 * @param y the absolute y position on the canvas
 * @param width the current width of the confetti
 * @param height the current height of a confetti
 * @param color the color that will be used to paint the confetti
 * @param isSelected tis the color is selected or not
 */
data class ColorBox(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val color: Color,
    val isSelected:Boolean
)
