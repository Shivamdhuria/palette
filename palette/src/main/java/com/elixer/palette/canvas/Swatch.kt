package com.elixer.palette.canvas

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Swatch(
    innerRadius: Float,
    colorSwatch: List<Color>,
    strokeWidth: Float,
    isDisplayed: Boolean,
    modifier: Modifier,
    startingAngle: Float,
    sweep: Float,
    spacer: Float = 0f
) {

    var radius = innerRadius
    for (item in colorSwatch) {
        ColorCanvas(
            radius, strokeWidth, item, startingAngle, sweep, isDisplayed, modifier
        )
        radius += strokeWidth + spacer
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewSwatch() {
    Swatch(
        300f, listOf(Green, Color.Blue, Color.Red), 100f, true, Modifier.size(500.dp, 900.dp),
        startingAngle = 270f, sweep = 30f, spacer = 140f
    )

}