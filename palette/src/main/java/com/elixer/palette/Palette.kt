package com.elixer.palette

import android.graphics.Rect
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Palette(
    defaultColor: Color,
    buttonSize: Dp = 80.dp,
    list: List<List<Color>>,
    innerRadiusFactor: Float = 4f,
    modifier: Modifier
) {

    val boxSizeWidth = remember { mutableStateOf(0f) }
    val boxSizeHeight = remember { mutableStateOf(0f) }

    //TODO: calculate this dynamically
    val maxShadeSize = 10
    var maxRadius: Float? = 0f

    /**
     * Area in which the particles are being drawn
     */
    val drawArea = remember { mutableStateOf(Rect()) }

    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)

    val innerRadius = drawArea.value.width() / 2 * innerRadiusFactor
    val degreeEach = 360f / list.size
    val radiusIncrement = drawArea.value.width() / 2 - innerRadius
    val radiusIncrementEach = 100f

    Log.e("degreeEach", degreeEach.toString())

    Canvas(
        modifier = modifier
            .onGloballyPositioned {
                drawArea.value = Rect(0, 0, it.size.width, it.size.height)
            }
            .rotate(20f),
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        maxRadius = canvasWidth / 2f
        val colorBoxLength = maxRadius!! - innerRadius / maxShadeSize
        val redsize = size / 3f
//        val colorBox = ColorBox(0f, 0f)
//        drawRect(
//            color = Green,
//            style = Stroke(10f)
//        )

        val colors = list[0]
        var radius = 600f
        Log.e("colors", colors.toString())

        for (item in colors) {
            drawArc(
                color = item,
                startAngle = 0F,
                sweepAngle = 2*degreeEach,
                useCenter = false,
                topLeft = offset(canvasWidth, radius),
                style = Stroke(width = 100f),
                size = Size(radius, radius)
            )
            radius += radiusIncrementEach + 100f
        }

        radius = 600f
        for (item in list[1]) {
            drawArc(
                color = item,
                startAngle = 2*degreeEach,
                sweepAngle = 2*degreeEach,
                useCenter = false,
                topLeft = offset(canvasWidth, radius),
                style = Stroke(width = 100f),
                size = Size(radius, radius)
            )
            radius += radiusIncrementEach + 100f
        }

        radius = 600f
        for (item in list[2]) {
            drawArc(
                color = item,
                startAngle = 4*degreeEach,
                sweepAngle = 2*degreeEach,
                useCenter = false,
                topLeft = offset(canvasWidth, radius),
                style = Stroke(width = 100f),
                size = Size(radius, radius)
            )
            radius += radiusIncrementEach + 100f
        }

        radius = 600f
        for (item in list[3]) {
            drawArc(
                color = item,
                startAngle = 6*degreeEach,
                sweepAngle = 2*degreeEach,
                useCenter = false,
                topLeft = offset(canvasWidth, radius),
                style = Stroke(width = 100f),
                size = Size(radius, radius)
            )
            radius += radiusIncrementEach + 100f
        }
//
//        drawArc(
//            color = Color(0xFF87DD77),
//            startAngle = 0F,
//            sweepAngle = 360f,
//            useCenter = false,
//            topLeft = offset(canvasWidth, 500f),
//            style = Stroke(width = 100f),
//            size = Size(500f, 500f)
//        )
//
//        drawArc(
//            color = Color(0xFF5F7ECC),
//            startAngle = 0F,
//            sweepAngle = 360f,
//            useCenter = false,
//            topLeft = offset(canvasWidth, 700f),
//            style = Stroke(width = 100f),
//            size = Size(700f, 700f)
//        )
//
//        drawArc(
//            color = Color(0xFF8C5FCC),
//            startAngle = 0F,
//            sweepAngle = degreeEach,
//            useCenter = false,
//            topLeft = offset(canvasWidth, 700f),
//            style = Stroke(width = 100f),
//            size = Size(700f, 700f)
//        )
    }
}

@Composable
fun ColorBox() {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawRect(
            color = Green,
            style = Stroke(50f)
        )
    })

}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewPalette() {
    Palette(defaultColor = Blue, modifier = Modifier.fillMaxSize(), list = Presets.custom())
}