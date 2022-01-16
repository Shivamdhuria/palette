package com.elixer.palette

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elixer.palette.canvas.Swatch

@Composable
fun ColorWheel(
    innerRadius: Float,
    swatches: List<List<Color>>,
    strokeWidth: Float,
    isDisplayed: Boolean,
    modifier: Modifier,
    spacer: Float = 0f
) {

    var startAngle = 0f
    val degreeEach = (360f - (swatches.size * spacer)) / (swatches.size)


    for (swatch in swatches) {
        Swatch(
            innerRadius = innerRadius,
            colorSwatch = swatch,
            strokeWidth = strokeWidth,
            isDisplayed = isDisplayed,
            modifier = modifier,
            startingAngle = startAngle,
            sweep = degreeEach,
            spacer = spacer
        )
        startAngle += degreeEach + spacer
    }

//    Swatch(innerRadius = innerRadius, colorSwatch = swatches[2], strokeWidth = strokeWidth, isDisplayed = isDisplayed, modifier = modifier, startingAngle = startAngle, sweep = 20f, spacer = 10f)

}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewColorWheel() {
    ColorWheel(
        innerRadius = 300f,
        swatches = Presets.custom(),
        strokeWidth = 10f,
        isDisplayed = true,
        modifier = Modifier.size(400.dp),
        spacer = 0f
    )
}