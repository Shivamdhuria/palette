package com.elixer.palette

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ColorSwatches(list: List<Color>) {
    LazyColumn {
        items(list.size) {
            list.forEach { somethingForLater ->
                ColorSwatch(colorValue = Color.Blue, size = Size(500f, 500f))
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewSwatches() {
//    ColorSwatch(colorValue = Color.Blue, size = Size(200f, 200f))

    ColorSwatches(list = listOf(Color.Blue, Color.Green))
}