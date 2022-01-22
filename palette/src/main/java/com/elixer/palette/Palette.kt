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
    val isPaletteDisplayed = remember { mutableStateOf(false) }
    val colorSelected = remember { mutableStateOf(defaultColor) }

    val showSelectedColorArc = remember { mutableStateOf(false) }
    val selectedColor = remember { mutableStateOf(Color(0xFFF71010)) }
    val selectedArch = remember {
        mutableStateOf(
            ColorArch(
                radius = 200f,
                strokeWidth = 30f,
                startingAngle = 240f,
                sweep = 40f,
                color = Color.Cyan,
                isSelected = false
            )
        )
    }


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

    val colorWheel = ColorWheel(radius = innerRadius, swatches = list, strokeWidth = colorStroke, isDisplayed = isPaletteDisplayed.value)

    Log.e("colorWheel", colorWheel.swatches.toString())

    val swatches = colorWheel.toSwatches()
    val colorArcsN = mutableListOf<ColorArch>()
    Log.e("swatches", swatches.toString())

    swatches.forEach {
        colorArcsN.addAll(it.toColorArch(isPaletteDisplayed.value))
    }

    val rad = mutableListOf<Float>()

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


    fun onColorSelected(colorArc: ColorArch) {
        colorSelected.value = colorArc.color
        isPaletteDisplayed.value = false
        showSelectedColorArc.value = true
        selectedArch.value = colorArc
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
            .background(Color.Black)
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
                                onColorSelected(it)
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

            drawArc(
                color = Color.White,
                startAngle = selectedArch.value.startingAngle - 2f,
                sweepAngle = selectedArch.value.sweep + 4f,
                useCenter = false,
                topLeft = Offset(centerX - selectedArch.value.radius, centerY - selectedArch.value.radius),
                style = Stroke(width = selectedArch.value.strokeWidth + 30f),
                size = Size(2 * selectedArch.value.radius, 2 * selectedArch.value.radius)
            )

            drawArc(
                color = selectedArch.value.color,
                startAngle = selectedArch.value.startingAngle,
                sweepAngle = selectedArch.value.sweep,
                useCenter = false,
                topLeft = Offset(centerX - selectedArch.value.radius, centerY - selectedArch.value.radius),
                style = Stroke(width = selectedArch.value.strokeWidth),
                size = Size(2 * selectedArch.value.radius, 2 * selectedArch.value.radius)
            )


        }
        LaunchButton(
            animationState = isPaletteDisplayed.value,
            selectedColor = colorSelected.value,
            onToggleAnimationState = { isPaletteDisplayed.value = !isPaletteDisplayed.value }
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