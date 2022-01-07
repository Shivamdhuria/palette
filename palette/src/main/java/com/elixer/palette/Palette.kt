package com.elixer.palette

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.palette.models.Swatch

@Composable
fun Palette(
    defaultColor: Color,
    buttonSize: Dp = 80.dp,
    list: List<List<Color>>,
    modifier: Modifier
) {

    val boxSizeWidth = remember { mutableStateOf(0f) }
    val boxSizeHeight = remember { mutableStateOf(0f) }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawRect(
            color = Color.Green,
            style = Stroke(50f)
        )

        drawCircle(
            color = Color.Black,
            radius = canvasWidth / 2,
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )



        drawCircle(
            color = defaultColor,
            radius = 45f,
        )
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 500)
@Composable
fun PreviewPalette() {
    Palette(defaultColor = Color.Blue, modifier = Modifier.fillMaxSize(), list = Presets.custom())
}