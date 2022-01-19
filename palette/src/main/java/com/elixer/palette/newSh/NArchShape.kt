package com.elixer.palette.newSh

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
    val strokeWidth: Float = 20f,
    val innerRadius: Float = 200f,
    val startingAngle: Float = 250f,
    val sweep: Float = 40f,
    val centerX: Float = 0f,
    val centerY:Float = 0f
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {

        val outerRadius = innerRadius + strokeWidth

        /**
         * Center of the circle
         */
        val startX = outerRadius + centerX;
        val startY = outerRadius + centerX;

        val outerRecSide = 2 * outerRadius
        val innerRecSide = 2 * innerRadius
        val difference = outerRadius - innerRadius

        val startingAngle = ((startingAngle) * Math.PI / 180)
        val endingAngle = ((this@NArchShape.startingAngle + sweep) * Math.PI / 180)

        val pathOuter = Path().apply {

            moveTo(startX, startY)

            //Outer arc cone
            val radius = outerRadius;

            val xOuterStart = (startX + radius * cos(startingAngle)).toFloat()
            val yOuterStart = (startY + radius * sin(startingAngle)).toFloat()

            val xOuterEnd = (startX + radius * cos(endingAngle)).toFloat()
            val yOuterEnd = (startY + radius * sin(endingAngle)).toFloat()

            val rectOuter = Rect(-outerRadius + centerX, -outerRadius + centerX, outerRadius + centerX, outerRadius + centerX)

            lineTo(xOuterEnd, yOuterEnd)
            lineTo(xOuterStart, yOuterStart)
            addArc(rectOuter, this@NArchShape.startingAngle, sweep)
        }

        val pathInner = Path().apply {
            moveTo(outerRadius + centerX, outerRadius + centerX)

//            val reactInner = Rect(-difference + offset, -difference + offset, difference + innerRecSide + offset, difference + innerRecSide + offset)

            val reactInner = Rect(-innerRadius + centerX, -innerRadius + centerX, innerRadius + centerX, innerRadius + centerX)

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
        return Outline.Generic(Path.combine(PathOperation.Difference, pathOuter, pathInner))
//        return Outline.Generic(pathOuter)
    }
}