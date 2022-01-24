package com.elixer.palette


import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.palette.geometry.Utils
import com.elixer.palette.models.ColorArch
import com.elixer.palette.models.ColorWheel
import com.elixer.palette.models.toColorArch
import com.elixer.palette.models.toSwatches
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan2

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

    var rotation by remember { mutableStateOf(0f) }

    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }

    val colorWheel = ColorWheel(
        radius = innerRadius, swatches = list,
        strokeWidth = colorStroke,
        isDisplayed = isPaletteDisplayed.value,
        spacerOutward = spacerOutward,
        spacerRotation = spacerRotation
    )

    val swatches = colorWheel.toSwatches()
    val colorArcsN = mutableListOf<ColorArch>()

    swatches.forEach {
        colorArcsN.addAll(it.toColorArch(isPaletteDisplayed.value))
    }

    val rad = mutableListOf<Float>()

    var rotationAngle by remember { mutableStateOf(0f) }
    var dragStartedAngle by remember { mutableStateOf(0f) }
    var oldAngle by remember { mutableStateOf(rotationAngle) }

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

    val rotationAnimatable: Float by animateFloatAsState(
        targetValue = rotationAngle,
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

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { it ->
                centerX = it.size.width / 2f
                centerY = it.size.height / 2f
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartedAngle = atan2(
                            y = centerX - offset.x,
                            x = centerY - offset.y
                        ) * (180f / Math.PI.toFloat()) * -1
                    },
                    onDragEnd = {
                        oldAngle = rotationAngle
                    }
                ) { change, _ ->

                    val touchAngle = atan2(
                        y = centerX - change.position.x,
                        x = centerY - change.position.y
                    ) * (180f / Math.PI.toFloat()) * -1

                    rotationAngle = oldAngle + (touchAngle - dragStartedAngle)

                    //we want to work with positive angles
                    if (rotationAngle > 360) {
                        rotationAngle -= 360
                    } else if (rotationAngle < 0) {
                        rotationAngle = 360 - abs(rotationAngle)
                    }
                }
            }
    ) {

        Canvas(modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { tapOffset ->
                        if (isPaletteDisplayed.value) {

                            /**
                             * Calculate angle between center and tapped offset
                             */

                            val angle = Utils.calculateAngle(centerX.dp.value, centerY.dp.value, tapOffset.x, tapOffset.y)

                            /**
                             * Calculate distance between center and tapped offset
                             */
                            val distance = Utils.calculateDistance(centerX, centerY, tapOffset.x, tapOffset.y)

                            colorArcsN.forEachIndexed { index, it ->
                                if (it.contains(angle, distance, rotationAnimatable)) {
                                    onColorSelected(it)
                                    return@forEachIndexed
                                } else {
                                    isPaletteDisplayed.value = false
                                }
                            }
                        }
                    },
                )
            }
        ) {

            colorArcsN.forEachIndexed { index, it ->
                val radius = rad[index]
                drawArc(
                    color = it.color,
                    startAngle = it.startingAngle + rotationAnimatable,
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
                topLeft = Offset(centerX - newSeletedAnimatable.value, centerY - newSeletedAnimatable.value),
                style = Stroke(width = selectedArch.value.strokeWidth + 30f),
                size = Size(2 * newSeletedAnimatable.value, 2 * newSeletedAnimatable.value)
            )

            drawArc(
                color = selectedArch.value.color,
                startAngle = selectedArch.value.startingAngle,
                sweepAngle = selectedArch.value.sweep,
                useCenter = false,
                topLeft = Offset(centerX - newSeletedAnimatable.value, centerY - newSeletedAnimatable.value),
                style = Stroke(width = selectedArch.value.strokeWidth),
                size = Size(2 * newSeletedAnimatable.value, 2 * newSeletedAnimatable.value)
            )
        }
        LaunchButton(
            animationState = isPaletteDisplayed.value,
            selectedColor = animatedColor,
            onToggleAnimationState = { isPaletteDisplayed.value = !isPaletteDisplayed.value }
        )
    }
}


@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewPalette() {
    Palette(defaultColor = Blue, modifier = Modifier.fillMaxSize(), list = Presets.custom())
}