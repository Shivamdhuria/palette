package com.elixer.palette.models

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
data class ColorShade(
    val colors: List<ColorBox>
)