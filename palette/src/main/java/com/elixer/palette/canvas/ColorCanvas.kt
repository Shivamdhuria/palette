package com.elixer.palette.canvas

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.elixer.palette.shape.ArchShape

@Composable
fun ColorCanvas(
    innerRadius: Float,
    strokeWidth: Float,
    color: Color,
    startAngle: Float,
    sweep: Float,
    isDisplayed: Boolean = true,
//    modifier: Modifier,

) {
    val radius: Float by animateFloatAsState(
        targetValue = if (isDisplayed) innerRadius else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    var isSelect = false
    val drawColor: Color by animateColorAsState(
        targetValue = if (isSelect) Color.Black else color
    )
    val arcColor = color

    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)
    Log.e("arcColor", color.toArgb().green.toString())
    Canvas(modifier = Modifier
        .size(300.dp, 300.dp)
        .clipToBounds()
        .background(Color(0x3EFFEB46))
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    Log.e("on Color tap Arc R", arcColor.toArgb().red.toString())
                    Log.e("on Color tap G", color.toArgb().green.toString())
                    Log.e("on Color tap B", color.toArgb().blue.toString())
                    isSelect = true
                }
            )

        },
        onDraw = {
//            drawRect(Color(0x3EFFEB46))
            drawArc(
                color = drawColor,
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
        700f, 200f, Color.Black, 0f, 360f, true,
//        Modifier.size(300.dp, 200.dp)
    )
//    ColorCanvas(
//        700f, 20f, Color.Blue, 180f, 270f, true, Modifier.size(500.dp, 900.dp)
//    )
}