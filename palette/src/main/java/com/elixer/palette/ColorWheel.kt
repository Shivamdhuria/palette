package com.elixer.palette

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorWheel(innerRadius: Float, colorRow: List<Color>, colorLength: Float, modifier: Modifier) {

    var startAngle = 0f
    val degreeEach = 360f/colorRow.size

    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)

    Canvas(modifier = modifier, onDraw = {

        for (item in colorRow) {
            drawArc(
                color = item,
                startAngle = startAngle,
                sweepAngle = degreeEach,
                useCenter = false,
                topLeft = offset(size.width, innerRadius),
                style = Stroke(width = colorLength),
                size = Size(innerRadius, innerRadius)
            )
            startAngle += degreeEach
        }
    })
}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewColorWheel() {
    ColorWheel(500f, listOf(Green, Color.Blue, Color.Red), 100f, Modifier.size(500.dp,900.dp))
}