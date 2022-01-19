package com.elixer.palette.newSh

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.elixer.palette.LaunchButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewPalette(
    modifier: Modifier,
    size: Dp = 100.dp
) {
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }

    val animatableSize = remember { Animatable(Size.Zero, Size.VectorConverter) }
    val (containerSize, setContainerSize) = remember { mutableStateOf<Size?>(null) }
    val (imageSize, setImageSize) = remember { mutableStateOf<Size?>(null) }
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    val animationState = remember { mutableStateOf(false) }


    BoxWithConstraints(
        modifier = modifier
            .width(size)
            .height(size)
            .background(Color.Cyan)
            .onSizeChanged { size ->
                setContainerSize(size.toSize())
            }
            .onGloballyPositioned { it ->
                centerX = it.size.width / 2f
                centerY = it.size.height / 2f
                Log.e("centerX $centerX", "centerY,$centerY")

            }
            .clipToBounds()
    ) {


        ColorSingleNew(
            innerRadius = 160f,
            strokeWidth = 30f,
            color = Color.Green,
            startAngle = 0f,
            sweep = 360f,
            isDisplayed = animationState.value,
            totalSize = maxWidth
        )
        LaunchButton(
            animationState = animationState.value,
            onToggleAnimationState = { animationState.value = !animationState.value }
        )


//        ColorSingle(
//            isColorVisible = animationState.value,
//            maxWidth = maxWidth,
//            maxHeight = maxHeight,
//            outerRadius = 240f,
//            innerRadius = 200f,
//        )
//
//        ColorSingle(
//            isColorVisible = animationState.value,
//            maxWidth = maxWidth,
//            maxHeight = maxHeight,
//            outerRadius = 150f,
//            innerRadius = 140f,
//        )

    }
}

@Composable
fun ColorSingle(
    isColorVisible: Boolean,
    maxWidth: Dp,
    maxHeight: Dp,
    strokeWidth: Float,
    innerRadius: Float,
    startingAngle: Float = 90f,
    sweepAngle: Float = 30f
) {


    Log.e("maxWid $maxWidth", "max He $maxHeight")
    Log.e("outer $strokeWidth", "inner $innerRadius")
    fun arcOffset(width: Float, size: Float): Offset =
        Offset(width / 2f - size, width / 2f - size)


    val nOffset = arcOffset(maxWidth.value, strokeWidth)
//    val x = maxHeight.value.dp/2
    val x = 0.dp
    val radius = strokeWidth


}


@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun NewreviewPalette() {
    NewPalette(
        size = 500.dp,
        modifier = Modifier
            .offset(20.dp, 50.dp)
    )
}