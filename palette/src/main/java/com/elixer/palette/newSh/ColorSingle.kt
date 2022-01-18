package com.elixer.palette.newSh

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColorSingleNew(
    innerRadius: Float,
    strokeWidth: Float,
    color: Color,
    startAngle: Float,
    sweep: Float,
    isDisplayed: Boolean = true,
    totalSize: Dp
) {
    val radius: Float by animateFloatAsState(
        targetValue = if (isDisplayed) 20f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    val outerRadius = innerRadius + strokeWidth
    Log.e("outerRadius", outerRadius.toString())
    Log.e("size", totalSize.toString())

    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)

    val xOff = 50.dp

    NArchedButton(
        onClick = { Log.e("button Clicked", color.toArgb().toString()) },
        modifier = Modifier.offset(60.dp, 60.dp),
        shape = NArchShape(innerRadius = radius, strokeWidth = 30f, startingAngle = startAngle, sweep),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)

    ) {

    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewColorSingle() {
    ColorSingleNew(
        100f, 20f, Color.Black, 0f, 360f,
        true, totalSize = 300.dp
    )
//    ColorCanvas(
//        700f, 20f, Color.Blue, 180f, 270f, true, Modifier.size(500.dp, 900.dp)
//    )
}