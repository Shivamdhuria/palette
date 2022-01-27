package com.elixer.palette.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.palette.Presets
import com.elixer.palette.constraints.HorizontalAlignment
import com.elixer.palette.constraints.HorizontalAlignment.*
import com.elixer.palette.constraints.VerticalAlignment
import com.elixer.palette.constraints.VerticalAlignment.*
import com.elixer.palette.geometry.Utils.Companion.calculateAngle
import com.elixer.palette.geometry.Utils.Companion.calculateDistance
import com.elixer.palette.models.ColorArc
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
    buttonSize: Dp = 100.dp,
    list: List<List<Color>>,
    innerRadius: Float = 440f,
    strokeWidth: Float = 120f,
    selectorColor: Color = Color.White,
    spacerRotation: Float = 20f,
    spacerOutward: Float = 20f,
    verticalAlignment: VerticalAlignment = Top,
    horizontalAlignment: HorizontalAlignment = Start,
    onColorSelected: (Color) -> Unit = {},
    buttonColorChangeAnimationDuration: Int = 500,
    selectedArchAnimationDuration: Int = 1000
) {

    val isPaletteDisplayed = remember { mutableStateOf(false) }
    val selectedArchAnimatable = remember { Animatable(0f) }
    val selectedColor = remember { mutableStateOf(defaultColor) }

    val animatedColor by animateColorAsState(
        selectedColor.value,
        tween(
            durationMillis = buttonColorChangeAnimationDuration,
            easing = LinearEasing
        )
    )

    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    val selectedArch = remember {
        mutableStateOf(
            ColorArc(
                radius = 0f,
                strokeWidth = 30f,
                startingAngle = 240f,
                sweep = 40f,
                color = Color.Cyan,
            )
        )
    }

    val colorWheel = ColorWheel(
        radius = innerRadius, swatches = list,
        strokeWidth = strokeWidth,
        isDisplayed = isPaletteDisplayed.value,
        spacerOutward = spacerOutward,
        spacerRotation = spacerRotation
    )

    val swatches = colorWheel.toSwatches()
    val colorArcs = mutableListOf<ColorArc>()

    swatches.forEach {
        colorArcs.addAll(it.toColorArch())
    }

    val radiusAnimatables = mutableListOf<Float>()

    var rotationAngle by remember { mutableStateOf(0f) }
    var dragStartedAngle by remember { mutableStateOf(0f) }
    var oldAngle by remember { mutableStateOf(rotationAngle) }

    colorArcs.forEachIndexed { index, it ->
        val radius: Float by animateFloatAsState(
            targetValue = if (isPaletteDisplayed.value) it.radius else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        radiusAnimatables.add(radius)
    }

    val rotationAnimatable: Float by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearEasing
        )
    )

    fun onColorSelected(colorArc: ColorArc) {
        onColorSelected(colorArc.color)
        selectedArch.value = colorArc
        isPaletteDisplayed.value = false
        selectedColor.value = colorArc.color

        coroutineScope.launch {
            selectedArchAnimatable.snapTo(
                colorArc.radius
            )
            selectedArchAnimatable.animateTo(
                targetValue = 0f,
                tween(
                    durationMillis = selectedArchAnimationDuration,
                    easing = LinearEasing
                )
            )
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
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
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { tapOffset ->
                        if (isPaletteDisplayed.value) {

                            /**
                             * Calculate angle between center and tapped offset
                             */

                            val angle = calculateAngle(centerX.dp.value, centerY.dp.value, tapOffset.x, tapOffset.y)

                            /**
                             * Calculate distance between center and tapped offset
                             */
                            val distance = calculateDistance(centerX, centerY, tapOffset.x, tapOffset.y)

                            colorArcs.forEachIndexed { index, it ->
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
            centerX = getCenterXCoordinate(horizontalAlignment, size.width)
            centerY = getCenterYCoordinate(verticalAlignment, size.height)

            colorArcs.forEachIndexed { index, it ->
                val radius = radiusAnimatables[index]
                this.drawColouredArc(it, rotationAnimatable, centerX, radius, centerY)
            }

            drawSelectorArc(selectedArch, selectorColor, rotationAnimatable, centerX, selectedArchAnimatable, centerY)
            drawColouredArc(selectedArch.value, rotationAnimatable, centerX, selectedArchAnimatable.value, centerY)
        }

        LaunchButton(
            selectedColor = animatedColor,
            onToggleAnimationState = { isPaletteDisplayed.value = !isPaletteDisplayed.value },
            offsetX = getCenterXCoordinate(horizontalAlignment, maxWidth.value).dp,
            offsetY = getCenterYCoordinate(verticalAlignment, maxHeight.value).dp,
            buttonSize = buttonSize
        )
    }
}

private fun DrawScope.drawSelectorArc(
    selectedArch: MutableState<ColorArc>,
    selectorColor: Color,
    rotationAnimatable: Float,
    centerX: Float,
    newSeletedAnimatable: Animatable<Float, AnimationVector1D>,
    centerY: Float
) {
    drawArc(
        color = selectorColor,
        startAngle = selectedArch.value.startingAngle + rotationAnimatable - 1f,
        sweepAngle = selectedArch.value.sweep + 2f,
        useCenter = false,
        topLeft = Offset(centerX - newSeletedAnimatable.value, centerY - newSeletedAnimatable.value),
        style = Stroke(width = selectedArch.value.strokeWidth + 20f),
        size = Size(2 * newSeletedAnimatable.value, 2 * newSeletedAnimatable.value)
    )
}

private fun DrawScope.drawColouredArc(
    it: ColorArc,
    rotationAnimatable: Float,
    centerX: Float,
    radius: Float,
    centerY: Float
) {
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

fun getCenterXCoordinate(horizontalAxis: HorizontalAlignment, maxX: Float): Float {
    return when (horizontalAxis) {
        is Start -> 0f
        is Center -> maxX / 2
        is End -> maxX
    }
}

fun getCenterYCoordinate(verticalAxis: VerticalAlignment, maxY: Float): Float {
    return when (verticalAxis) {
        is Top -> 0f
        is Middle -> maxY / 2
        is Bottom -> maxY
    }
}


@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewPalette() {
    Palette(
        defaultColor = Blue,
        list = Presets.custom(),
    )
}