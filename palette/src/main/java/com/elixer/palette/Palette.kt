package com.elixer.palette

import android.graphics.Rect
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
    innerRadius: Float = 200f,
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
        Offset(width/2f  - size/2f, width/2f  - size/2f)

    val radius = 200f

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                drawArea.value = Rect(0, 0, it.size.width, it.size.height)
            }
            .rotate(0f),
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        maxRadius = canvasWidth / 2f
        val colorBoxLength = maxRadius!! - innerRadius / maxShadeSize
        val redsize = size / 3f
//        val colorBox = ColorBox(0f, 0f)
        drawRect(
            color = Green,
            style = Stroke(10f)
        )

        drawCircle(
            color = Color.Red,
            radius = canvasWidth / 2,
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )

        drawCircle(
            color = Color.Red,
            radius = canvasWidth / 4,
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )

        drawArc(
            color = Color(0xFF3F51B5),
            startAngle = 0F,
            sweepAngle = 70f,
            useCenter = false,
            style = Stroke(width = 80f),

            )

        drawArc(
            color = Color(0xFF51B53F),
            startAngle = 0F,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = offset(canvasWidth,500f),
            style = Stroke(width = 80f),
            size = Size(500f, 500f)

        )

        drawArc(
            brush = Brush.verticalGradient(
                colors = listOf(Blue, Magenta, Green),
//                start = Offset(0.0f, 0.0f),
//                end = Offset(0.0f, 100.0f)

            ),
            startAngle = 180F,
            sweepAngle = 80f,
            useCenter = true,
            style = Stroke(width = 80f),

            )

        drawArc(
            color = Color.DarkGray,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(size.width / 4, size.height / 4),
            size = size / 2f,
            style = Stroke(10.0f)
        )

        drawArc(
            color = Blue,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(size.width / 9, size.height / 9),
            size = size / 3f,
            style = Stroke(10.0f)
        )


//        drawRect(
//
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

@Preview(showBackground = true, widthDp = 500, heightDp = 500)
@Composable
fun PreviewPalette() {
    Palette(defaultColor = Blue, modifier = Modifier.fillMaxSize(), list = Presets.custom())
}