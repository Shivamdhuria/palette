package com.elixer.palette.canvas

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.cos
import kotlin.math.sin

class NArchShape(
    val outerRadius: Float = 300f,
    val innerRadius: Float = 200f,
    val startingAngle: Float = 250f,
    val sweep: Float = 40f
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {

        /**
         * Center of the circle
         */
        val startX = outerRadius;
        val startY = outerRadius;

        val outerRecSide = 2 * outerRadius
        val innerRecSide = 2 * innerRadius
        val difference = outerRadius - innerRadius

        val startingAngle = ((startingAngle) * Math.PI / 180)
        val endingAngle = ((this@NArchShape.startingAngle + sweep) * Math.PI / 180)

        val pathCircle = Path().apply {
            moveTo(0f, 0f)
            val rectOuter = Rect(0f, 0f, outerRecSide, outerRecSide)
            addArc(rectOuter, 0f, 360f)
        }

        val pathOuter = Path().apply {

            moveTo(outerRadius, outerRadius)

            //Outer arc cone
            val radius = outerRadius;

            val xOuterStart = (startX + radius * cos(startingAngle)).toFloat()
            val yOuterStart = (startY + radius * sin(startingAngle)).toFloat()

            val xOuterEnd = (startX + radius * cos(endingAngle)).toFloat()
            val yOuterEnd = (startY + radius * sin(endingAngle)).toFloat()

            val rectOuter = Rect(0f, 0f, outerRecSide, outerRecSide)

            lineTo(xOuterEnd, yOuterEnd)
            lineTo(xOuterStart, yOuterStart)
            addArc(rectOuter, this@NArchShape.startingAngle, sweep)
        }

        val pathInner = Path().apply {
            moveTo(outerRadius, outerRadius)

            val reactInner = Rect(difference, difference, difference + innerRecSide, difference + innerRecSide)

            //inner arc cone
            val xInnerStart = (startX + innerRadius * cos(startingAngle)).toFloat()
            val yInnerStart = (startY + innerRadius * sin(startingAngle)).toFloat()

            val xInnerEnd = (startX + innerRadius * cos(endingAngle)).toFloat()
            val yInnerEnd = (startY + innerRadius * sin(endingAngle)).toFloat()
//
            lineTo(xInnerEnd, yInnerEnd)
            lineTo(xInnerStart, yInnerStart)
            addArc(reactInner, this@NArchShape.startingAngle, sweep)
        }

        return Outline.Generic(pathCircle)
    }
}