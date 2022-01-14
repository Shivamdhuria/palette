package com.elixer.palette.shape

import android.util.Log
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.cos
import kotlin.math.sin

class ArchShape(
    val outerRadius: Float = 600f,
    val innerRadius: Float = 300f,
    val startingAngle: Float = 210f,
    val sweep: Float =90f
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {


            moveTo(0f, 0f)
            relativeLineTo(outerRadius,outerRadius)
            val outerRecSide = 2 * outerRadius
            val innerRecSide = 2 * innerRadius
            val difference = outerRadius - innerRadius


            Log.e("heigh ${innerRadius}", "width ${innerRadius}")

            val react = Rect(innerRadius, 0f, innerRadius, innerRadius)


            val rectBttum = Rect(difference, difference, difference+innerRecSide, difference+innerRecSide)
//            lineTo(innerRadius, innerRadius)
            val startX = outerRadius;
            val startY = outerRadius;
            val radius = outerRadius;
            val angle =  ((startingAngle) * Math.PI / 180)
            val stopX =  (startX + radius * cos(angle)).toFloat()
            val stopY =  (startY + radius * sin(angle)).toFloat()

            val angleN =  ((startingAngle+ sweep) * Math.PI / 180)
            val stopXN =  (startX + radius * cos(angleN)).toFloat()
            val stopYN =  (startY + radius * sin(angleN)).toFloat()


            Log.e("stop X $stopX", "stop y $stopY")
//            moveTo(startX,startY)
//            lineTo( stopX, stopY);
//            close()
            val rectTop = Rect(0f, 0f, outerRecSide, outerRecSide)
            addArc(rectTop, startingAngle, sweep)
//            addArc(rectBttum, startingAngle, sweep)

            moveTo(outerRadius , outerRadius)
            lineTo(stopXN, stopYN)
            lineTo(stopX, stopY)



            close()







//            val deg = Math.toRadians(-startingAngle.toDouble())
//            val xOuter = outerRadius+ cos(deg) - outerRecSide
//            val yOuter = outerRadius+ sin(deg) - outerRecSide
//
//            val xInner = innerRadius+ cos(deg) - outerRecSide
//            val yInner = innerRadius+ sin(deg) - outerRecSide
//
//            moveTo(xOuter.toFloat(), yOuter.toFloat())
//            lineTo(xInner.toFloat(),yInner.toFloat())
//
//            val degN = Math.toRadians(-(startingAngle+sweep).toDouble())
//            val xOuterN = outerRadius+ cos(degN) - outerRecSide
//            val yOuterN = outerRadius+ sin(degN) - outerRecSide
//
//            val xInnerN = innerRadius+ cos(degN) - outerRecSide
//            val yInnerN = innerRadius+ sin(degN)- outerRecSide
//            moveTo(xOuterN.toFloat(), yOuterN.toFloat())
//            lineTo(xInnerN.toFloat(),yInnerN.toFloat())

        }
        return Outline.Generic(path)
    }
}