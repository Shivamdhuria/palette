package com.elixer.palette.newSh

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.size
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
    innerRadius: Float = 100f,
    strokeWidth: Float = 70f,
    color: Color,
    startAngle: Float,
    sweep: Float,
    isDisplayed: Boolean = true,
    centerX: Dp,
    centerY:Dp
) {
    val outerRadius = innerRadius + strokeWidth

    val radius: Float by animateFloatAsState(
        targetValue = if (isDisplayed) innerRadius else 90f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    Log.e("outerRadius", outerRadius.toString())
    Log.e("size", centerX.value.toString())

    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)

    NArchedButton(
        {
            //Adding offset removed click
            Log.e("button Clicked", color.toArgb().toString())
        }, modifier = Modifier.size(800.dp, 500.dp),
        shape =
        NArchShape(strokeWidth , radius,180f, 360f,
//            totalSize.value/2 + outerRadius)
            centerX.value, centerY.value),
    ) {
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewColorSingle() {
    ColorSingleNew(
        color = Color.Black, startAngle = 0f, sweep = 360f,
        isDisplayed = true, centerX = 300.dp, centerY = 0.dp
    )
//    ColorCanvas(
//        700f, 20f, Color.Blue, 180f, 270f, true, Modifier.size(500.dp, 900.dp)
//    )
}