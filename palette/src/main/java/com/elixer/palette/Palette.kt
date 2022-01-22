package com.elixer.palette


import android.util.Log
import androidx.compose.animation.animateColorAsState
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
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Palette(
    defaultColor: Color,
    buttonSize: Dp = 80.dp,
    list: List<List<Color>>,
    innerRadius: Float = 340f,
    colorStroke: Float = 120f,
    modifier: Modifier,
    spacerRotation: Float = 1f,
    spacerOutward: Float = 500f
) {
    //Newl,
    val isPaletteDisplayed = remember { mutableStateOf(false) }
    val colorSelected = remember { mutableStateOf(defaultColor) }

    val animatedColor = animateColorAsState(colorSelected.value)


    val showSelectedColorArc = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    val selectedArch = remember {
        mutableStateOf(
            ColorArch(
                radius = 0f,
                strokeWidth = 30f,
                startingAngle = 240f,
                sweep = 40f,
                color = Color.Cyan,
                isSelected = false
            )
        )
    }


    val newSeletedAnimatable = remember { Animatable(0f) }


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

    val colorWheel = ColorWheel(
        radius = innerRadius, swatches = list,
        strokeWidth = colorStroke,
        isDisplayed = isPaletteDisplayed.value,
        spacerOutward = spacerOutward,
        spacerRotation = spacerRotation
    )

    Log.e("colorWheel", colorWheel.swatches.toString())

    val clrWheels = mutableListOf<List<List<Color>>>()
    val circleNo = 0

    val color1 = mutableListOf<Color?>()
    val color2 = mutableListOf<Color?>()
    list.forEach {
        color1.add(it[0])
        color2.add(it[0])
    }


    Collections.rotate(color2, -5)

    val degree = 360f / 34
    val swatches = colorWheel.toSwatches()
    val colorArcsN = mutableListOf<ColorArch>()
    Log.e("swatches", swatches.toString())

    swatches.forEach {
        colorArcsN.addAll(it.toColorArch(isPaletteDisplayed.value))
    }

    val rad = mutableListOf<Float>()

    var startingRad = 200f
    for (i in 1..10) {
        val radius: Float by animateFloatAsState(
            targetValue = if (isPaletteDisplayed.value) startingRad else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        rad.add(radius)
        startingRad = startingRad + 50f
    }

    colorArcsN.forEachIndexed { index, it ->
        val radius: Float by animateFloatAsState(
            targetValue = if (isPaletteDisplayed.value) it.radius else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        rad.add(radius)
    }


    val radius5: Float by animateFloatAsState(
        targetValue = if (isPaletteDisplayed.value) 600f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    fun onColorSelected(colorArc: ColorArch) {
        selectedArch.value = colorArc
        showSelectedColorArc.value = true
        isPaletteDisplayed.value = false
        showSelectedColorArc.value = true
        coroutineScope.launch {
            newSeletedAnimatable.snapTo(
                colorArc.radius
            )
            newSeletedAnimatable.animateTo(
                targetValue = 0f,
                tween(
                    durationMillis = 700,
                    easing = LinearEasing
                )
            )
            colorSelected.value = colorArc.color
        }


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
//            .background(Color(0xFF052F75))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { tapOffset ->
                        if (isPaletteDisplayed.value) {

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
                                    onColorSelected(it)
                                    return@forEachIndexed
                                } else {
                                    isPaletteDisplayed.value = false
                                }
                            }
                        }
                    }
                )
            }) {
            Log.e("max width ", maxWidth.value.toString())
            Log.e("max height ", maxHeight.value.toString())


//            colorArcsN.forEachIndexed { index, it ->
//                val radius = rad[index]
//                drawArc(
//                    color = it.color,
//                    startAngle = it.startingAngle,
//                    sweepAngle = it.sweep,
//                    useCenter = false,
//                    topLeft = Offset(centerX - radius, centerY - radius),
//                    style = Stroke(width = it.strokeWidth),
//                    size = Size(2 * radius, 2 * radius)
//                )
//            }
            val radius = 200f
            Log.e("color1", color1.toString())
            var startAngle = 0f


            color1.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[0], centerY - rad[0]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[0], 2 * rad[0])
                    )

                }
                startAngle += degree
            }


            startAngle = 0f
            color2.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[1], centerY - rad[1]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[1], 2 * rad[1])
                    )

                }
                startAngle += degree
            }

            startAngle = 0f
            color1.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[2], centerY - rad[2]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[2], 2 * rad[2])
                    )

                }
                startAngle += degree
            }


            startAngle = 0f
            color2.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[3], centerY - rad[3]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[3], 2 * rad[3])
                    )

                }
                startAngle += degree
            }


            startAngle = 0f
            color1.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[5], centerY - rad[5]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[5], 2 * rad[5])
                    )

                }
                startAngle += degree
            }



            startAngle = 0f
            color2.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[6], centerY - rad[6]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[6], 2 * rad[6])
                    )

                }
                startAngle += degree
            }



            startAngle = 0f
            color1.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[7], centerY - rad[7]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[7], 2 * rad[7])
                    )

                }
                startAngle += degree
            }


            startAngle = 0f
            color2.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[8], centerY - rad[8]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[8], 2 * rad[8])
                    )

                }
                startAngle += degree
            }


            startAngle = 0f
            color1.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[9], centerY - rad[9]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[9], 2 * rad[9])
                    )

                }
                startAngle += degree
            }


            startAngle = 0f
            color2.forEach { color ->
                color?.let {
                    drawArc(
                        color = it,
                        startAngle = startAngle,
                        sweepAngle = degree,
                        useCenter = false,
                        topLeft = Offset(centerX - rad[10], centerY - rad[10]),
                        style = Stroke(width = 100f),
                        size = Size(2 * rad[10], 2 * rad[10])
                    )

                }
                startAngle += degree
            }
//            drawArc(
//                color = Color.White,
//                startAngle = selectedArch.value.startingAngle - 2f,
//                sweepAngle = selectedArch.value.sweep + 4f,
//                useCenter = false,
//                topLeft = Offset(centerX - newSeletedAnimatable.value, centerY - newSeletedAnimatable.value),
//                style = Stroke(width = selectedArch.value.strokeWidth + 30f),
//                size = Size(2 * newSeletedAnimatable.value, 2 * newSeletedAnimatable.value)
//            )
//
//            drawArc(
//                color = selectedArch.value.color,
//                startAngle = selectedArch.value.startingAngle,
//                sweepAngle = selectedArch.value.sweep,
//                useCenter = false,
//                topLeft = Offset(centerX - newSeletedAnimatable.value, centerY - newSeletedAnimatable.value),
//                style = Stroke(width = selectedArch.value.strokeWidth),
//                size = Size(2 * newSeletedAnimatable.value, 2 * newSeletedAnimatable.value)
//            )


    }
    LaunchButton(
        animationState = isPaletteDisplayed.value,
        selectedColor = animatedColor,
        onToggleAnimationState = { isPaletteDisplayed.value = !isPaletteDisplayed.value }
    )
}


/**
 * contains  animatables for all shades
 */
val animatables = mutableListOf<Animatable<Float, AnimationVector1D>>()

var degreeAddition = 0f
list.forEachIndexed {
    i, e ->
    animatables.add(remember { Animatable(0f) })
    degreeAddition += degreeEach
}
}


@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewPalette() {
    Palette(defaultColor = Blue, modifier = Modifier.fillMaxSize(), list = Presets.custom())
}