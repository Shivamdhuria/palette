package com.elixer.palette.canvas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorCanvas(
    innerRadius: Float,
    strokeWidth: Float,
    color: Color,
    startAngle: Float,
    sweep: Float,
    isDisplayed: Boolean = true,
    modifier: Modifier
) {
    val radius: Float by animateFloatAsState(if (isDisplayed) innerRadius else 0f)


    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)

    Canvas(modifier = modifier, onDraw = {
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweep,
            useCenter = false,
            topLeft = offset(size.width, radius),
            style = Stroke(width = strokeWidth),
            size = Size(radius, radius)
        )
    })
}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewColorWheel() {
    ColorCanvas(
        500f,20f,Color.Magenta,180f,270f
        , true, Modifier.size(500.dp, 900.dp))
    ColorCanvas(
        700f,20f,Color.Blue,180f,270f
        , true, Modifier.size(500.dp, 900.dp))
}