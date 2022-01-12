package com.elixer.palette

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ColorSwatch(colorValue: Color, size: Size) {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawRect(color = colorValue, size = size)
    })
}

@Preview(showBackground = true, widthDp = 500, heightDp = 500)
@Composable
fun PreviewMainScreen() {
    ColorSwatch(colorValue = Color.Blue, size = Size(200f, 200f))
}