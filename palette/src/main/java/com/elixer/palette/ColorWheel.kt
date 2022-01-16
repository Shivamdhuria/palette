package com.elixer.palette

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elixer.palette.canvas.ColorCanvas

@Composable
fun ColorWheel(
    innerRadius: Float,

    colorRow: List<Color>,
    colorLength: Float,
    isDisplayed: Boolean,
    modifier: Modifier
) {

    var startAngle = 0f
    val degreeEach = 360f / colorRow.size

    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)


    for (item in colorRow) {
        ColorCanvas(
            innerRadius, colorLength, item, startAngle, degreeEach, isDisplayed, modifier
        )
        startAngle += degreeEach
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewColorWheel() {
    ColorWheel(500f, listOf(Green, Color.Blue, Color.Red), 100f,true, Modifier.size(500.dp, 900.dp))
    ColorWheel(700f, listOf(Color.Magenta, Color.Blue, Color.Red), 50f, true,Modifier.size(500.dp, 900.dp))

}