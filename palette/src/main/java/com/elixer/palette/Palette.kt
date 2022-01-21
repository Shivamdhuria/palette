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
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.palette.geometry.ColorArc
import com.elixer.palette.geometry.Utils
import com.elixer.palette.models.ColorBox

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Palette(
    defaultColor: Color,
    buttonSize: Dp = 80.dp,
    list: List<List<Color>>,
    innerRadius: Float = 240f,
    colorStroke: Float = 130f,
    modifier: Modifier
) {
    //New
    val animationState = remember { mutableStateOf(true) }


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

    val colorArcs = ArrayList<ColorArc>()
    colorArcs.add(
        ColorArc(
            160f, 40f,
            Color.Yellow,
            innerRadius,
            colorStroke,
        )
    )

    val radius: Float by animateFloatAsState(
        targetValue = if (animationState.value) innerRadius else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )


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

                        Log.e("tapOffset x", tapOffset.x.toString())
                        Log.e("center x", centerX.toString())
                        Log.e("diff x", (tapOffset.x - centerX.dp.value).toString())

                        /**
                         * Calculate angle between center and tapped offset
                         */

                        val angle = Utils.calculateAngle(centerX.dp.value, centerY.dp.value, tapOffset.x, tapOffset.y)
                        Log.e("angle", angle.toString())

                        /**
                         * Calculate distance between center and tapped offset
                         */
                        val distance = Utils.calculateDistance(centerX, centerY, tapOffset.x, tapOffset.y)

                        Log.e("distance", distance.toString())

                        var index = 0
                        colorArcs.forEachIndexed { index, it ->
                            if (it.contains(angle, distance)) {
                                Log.e("Found", it.color.toString())
                                return@forEachIndexed
                            }
                        }
                    }
                )
            }) {
            Log.e("max width ", maxWidth.value.toString())
            Log.e("max height ", maxHeight.value.toString())

//            drawRect(
//                color = Color(0xFF9D2933),
//                size = Size(
//                    width = 240f,
//                    height = 440f
//                ),
//                topLeft = Offset(
//                    x = 240f,
//                    y = 240f
//                )
//            )
            fun offset(width: Float, height: Float, size: Float): Offset =
                Offset(width / 2f - size / 2f, height / 2f - size / 2f)
            colorArcs.forEach {
                drawArc(
                    color = it.color,
                    startAngle = it.startingAngle,
                    sweepAngle = it.sweep,
                    useCenter = false,
//                topLeft = offset(maxWidth.value,maxHeight.value,100f),
                    topLeft = Offset(centerX - radius , centerY - radius ),
                    style = Stroke(width = it.strokeWidth),
                    size = Size(2*radius, 2*radius)
                )
            }

            val rectRadius = 2*radius + colorStroke

            drawRect(
                color = Color(0x609D2933),
                size = Size(rectRadius, rectRadius),
                topLeft = Offset(centerX - rectRadius / 2, centerY - rectRadius / 2)
            )
        }

//        LaunchButton(
//            animationState = animationState.value,
//            onToggleAnimationState = { animationState.value = !animationState.value }
//        )
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