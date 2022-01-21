package com.elixer.palette


import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.palette.models.ColorBox

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Palette(
    defaultColor: Color,
    buttonSize: Dp = 80.dp,
    list: List<List<Color>>,
    innerRadius: Float = 400f,
    colorStroke: Float = 80f,
    modifier: Modifier
) {
    //New
    val animationState = remember { mutableStateOf(false) }


    /**
     * Color boxes to draw
     */
    val particles = remember { mutableStateOf(emptyList<ColorBox>()) }

    /**
     * degree for each shade
     */
    val degreeEach = 360f / list.size

    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }

    val dotRects = ArrayList<Rect>()
    dotRects.add(Rect(0f, 0f, 240f, 440f))

    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned { it ->
            centerX = it.size.width / 2f
            centerY = it.size.height / 2f
            Log.e("centerX $centerX", "centerY,$centerY")
        }
//        .clipToBounds()
    ) {

        Canvas(modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { tapOffset ->
                        var index = 0
                        for (rect in dotRects) {
                            if (rect.contains(tapOffset)) {
                                Log.e("rect clicked", rect.toString())
                                break // don't need to check other points,
                                // so break
                            }
                            index++
                        }
                    }
                )
            }) {
            Log.e("max width ", maxWidth.value.toString())
            Log.e("max height ", maxHeight.value.toString())

            drawRect(
                color = Color(0xFF9D2933),
                size = Size(
                    width = 240f,
                    height = 440f
                ),
                topLeft = Offset(
                    x = 0f,
                    y = 0f
                )
            )
            fun offset(width: Float,height:Float, size: Float): Offset =
                Offset(width / 2f - size / 2f, height / 2f - size / 2f)

            drawArc(
                color = Color.Blue,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
//                topLeft = offset(maxWidth.value,maxHeight.value,100f),
                topLeft = Offset(centerX - 300,centerY - 300),
                style = Stroke(width = 30f),
                size = Size(600f, 600f)
            )


        }


//        ColorWheel(
//            innerRadius,
//            list,
//            colorStroke,
//            animationState.value,
//            Modifier.size(size = maxHeight),
//            2f
//        )
        LaunchButton(
            animationState = animationState.value,
            onToggleAnimationState = { animationState.value = !animationState.value }
        )
    }


    /**
     * contains  animatables for all shades
     */
    val animatables = mutableListOf<Animatable<Float, AnimationVector1D>>()

    var degreeAddition = 0f
    list.forEachIndexed { i, e ->
        animatables.add(remember { Animatable(0f) })
        degreeAddition += degreeEach
    }
}


@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewPalette() {
    Palette(defaultColor = Blue, modifier = Modifier.fillMaxSize(), list = Presets.custom())
}