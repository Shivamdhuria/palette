package com.elixer.palette


import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.red
import com.elixer.palette.geometry.Utils
import com.elixer.palette.models.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Palette(
    defaultColor: Color,
    buttonSize: Dp = 80.dp,
    list: List<List<Color>>,
    innerRadius: Float = 340f,
    colorStroke: Float = 120f,
    modifier: Modifier
) {
    //New
    val animationState = remember { mutableStateOf(false) }
    val colorSelected = remember { mutableStateOf(defaultColor) }


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

    val colorWheel = ColorWheel(radius = innerRadius, swatches = list, strokeWidth = colorStroke, isDisplayed = animationState.value)

    Log.e("colorWheel", colorWheel.swatches.toString())

    val swatches = colorWheel.toSwatches()
    val colorArcsN = mutableListOf<ColorArch>()
    Log.e("swatches", swatches.toString())

    swatches.forEach {
        colorArcsN.addAll(it.toColorArch(animationState.value))
    }

    val rad = mutableListOf<Float>()

    colorArcsN.forEachIndexed { index, it ->
        val radius: Float by animateFloatAsState(
            targetValue = if (animationState.value) it.radius else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        rad.add(radius)
    }


    val dotRects = ArrayList<Rect>()
    dotRects.add(Rect(0f, 0f, 240f, 440f))

//    val colorArcs = ArrayList<ColorArc>()
//    colorArcs.add(
//        ColorArc(
//            0f, 360f,
//            Color.Cyan,
//            innerRadius,
//            colorStroke,
//            animationState.value
//        )
//    )
//
//    colorArcs.add(
//        ColorArc(
//            0f, 360f,
//            Color.Red,
//            innerRadius + colorStroke,
//            colorStroke,
//            animationState.value
//
//        )
//    )

//    val radiusOne: Float by animateFloatAsState(
//        targetValue = if (animationState.value) colorArcs[0].innerRadius else 0f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioLowBouncy,
//            stiffness = Spring.StiffnessVeryLow
//        )
//    )
//
//    val radiusTwo: Float by animateFloatAsState(
//        targetValue = if (animationState.value) colorArcs[1].innerRadius else 0f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioLowBouncy,
//            stiffness = Spring.StiffnessVeryLow
//        )
//    )

    fun onColorSelected(color: Color) {
        colorSelected.value = color
    }



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
                        colorArcsN.forEachIndexed { index, it ->
                            if (it.contains(angle, distance)) {
                                Log.e("Found", it.color.toArgb().red.toString())
                                onColorSelected(it.color)
                                return@forEachIndexed
                            }
                        }
                    }
                )
            }) {
            Log.e("max width ", maxWidth.value.toString())
            Log.e("max height ", maxHeight.value.toString())


            colorArcsN.forEachIndexed { index, it ->
                val radius = rad[index]
                drawArc(
                    color = it.color,
                    startAngle = it.startingAngle,
                    sweepAngle = it.sweep,
                    useCenter = false,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    style = Stroke(width = it.strokeWidth),
                    size = Size(2 * radius, 2 * radius)
                )
            }



            Log.e("colorArchN", colorArcsN.toString())
            val radius = 200f
//            drawArc(
//                color = colorOne.color,
//                startAngle = colorOne.startingAngle,
//                sweepAngle = colorOne.sweep,
//                useCenter = false,
//                topLeft = Offset(centerX - radius, centerY - radius),
//                style = Stroke(width = colorOne.strokeWidth),
//                size = Size(2 * radius, 2 * radius)
//            )

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
//            colorArcs[0].let { it ->
//                drawArc(
//                    color = it.color,
//                    startAngle = it.startingAngle,
//                    sweepAngle = it.sweep,
//                    useCenter = false,
//                    topLeft = Offset(centerX - radiusOne, centerY - radiusOne),
//                    style = Stroke(width = it.strokeWidth),
//                    size = Size(2 * radiusOne, 2 * radiusOne)
//                )
//            }
//
//            colorArcs[1].let { it ->
//                drawArc(
//                    color = it.color,
//                    startAngle = it.startingAngle,
//                    sweepAngle = it.sweep,
//                    useCenter = false,
//                    topLeft = Offset(centerX - radiusTwo, centerY - radiusTwo),
//                    style = Stroke(width = it.strokeWidth),
//                    size = Size(2 * radiusTwo, 2 * radiusTwo)
//                )
//            }

//            val rectRadius = 2 * radius + colorStroke
//            val rectRadiusInner = 2 * radius - colorStroke

//            drawRect(
//                color = Color(0x609D2933),
//                size = Size(rectRadius, rectRadius),
//                topLeft = Offset(centerX - rectRadius / 2, centerY - rectRadius / 2)
//            )
//
//            drawRect(
//                color = Color(0x4D2196F3),
//                size = Size(rectRadiusInner, rectRadiusInner),
//                topLeft = Offset(centerX - rectRadiusInner / 2, centerY - rectRadiusInner / 2)
//            )
        }

        LaunchButton(
            animationState = animationState.value,
            selectedColor = colorSelected.value,
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