package com.elixer.palette

import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.atan2

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Palette(
    defaultColor: Color,
    buttonSize: Dp = 80.dp,
    list: List<List<Color>>,
    innerRadius: Float = 900f,
    colorLength: Float = 100f,
    modifier: Modifier
) {

    val boxSizeWidth = remember { mutableStateOf(0f) }
    val boxSizeHeight = remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()
    val rotationAnimatable = remember { Animatable(0f) }


    //TODO: calculate this dynamically
    val maxShadeSize = 10
    var maxRadius: Float? = 0f

    /**
     * Area in which the particles are being drawn
     */
    val drawArea = remember { mutableStateOf(Rect()) }

    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)

    /**
     * degree for each shade
     */
    val degreeEach = 360f / list.size

    val animateFloat0 = remember { Animatable(0f) }
    val animateFloat1 = remember { Animatable(10f) }
    var touchX by remember { mutableStateOf(0f) }
    var touchY by remember { mutableStateOf(0f) }
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }

    /**
     * contains  animatables for all shades
     */
    val animatables = mutableListOf<Animatable<Float, AnimationVector1D>>()

//    list.forEachIndexed {i,e->
//        animatables[i] = remember { Animatable(0f) }
//    }


    Log.e("degreeEach", degreeEach.toString())

    Canvas(
        modifier = modifier
            .onGloballyPositioned {
                drawArea.value = Rect(0, 0, it.size.width, it.size.height)
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.height / 2f
            }
            .rotate(rotationAnimatable.value)
//            .pointerInput(Unit) {
//                detectHorizontalDragGestures { change, dragAmount ->
//                    coroutineScope.launch {
//                        rotationAnimatable.animateTo(
//                            targetValue = - dragAmount,
//                            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
//                        )
//                    }
//                }
//            },
            .pointerInteropFilter { event ->
                touchX = event.x
                touchY = event.y
                val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()

                when (event.action) {
                    ACTION_DOWN, ACTION_MOVE -> {
                        Log.e("angle", angle.toString())
                        coroutineScope.launch {
                            if (angle > 0) {
                                rotationAnimatable.animateTo(
                                    targetValue = rotationAnimatable.value + 15,
                                    animationSpec = tween(durationMillis = 150, easing = LinearEasing)

                                )
                            } else {
                                rotationAnimatable.animateTo(
                                    targetValue = rotationAnimatable.value - 20,
                                    animationSpec = tween(durationMillis = 100, easing = LinearEasing)

                                )
                            }
                        }
                        true
                    }
                    else -> false
                }
            }

    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        maxRadius = canvasWidth / 2f

        val shades = list
        var radius = innerRadius
        val degreeIncrement = degreeEach
        var multiplier = 1
        var startAngle = 0f
        shades.forEach { shade ->

            shade.forEach {
                drawArc(
                    color = it,
                    startAngle = startAngle,
                    sweepAngle = animateFloat0.value,
                    useCenter = false,
                    topLeft = offset(canvasWidth, radius),
                    style = Stroke(width = 100f),
                    size = Size(radius, radius)
                )
                radius += colorLength + 100f
            }
            startAngle += degreeEach
            radius = innerRadius
            multiplier++


        }

//        shades.forEach { shade ->
//
//            shade.forEach {
//                drawArc(
//                    color = it,
//                    startAngle = 50f,
//                    sweepAngle = 30f,
//                    useCenter = false,
//                    topLeft = offset(canvasWidth, radius),
//                    style = Stroke(width = 100f),
//                    size = Size(radius, radius)
//                )
//                radius += colorLength + 100f
//            }
//            startAngle += startAngle
//            radius = innerRadius
//            multiplier++
//
//
//        }
        coroutineScope.launch {
            animateFloat0.animateTo(
                targetValue = degreeIncrement,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }

    }


//        for (item in colors) {
//            drawArc(
//                color = item,
//                startAngle = 0F,
//                sweepAngle = 2 * degreeEach,
//                useCenter = false,
//                topLeft = offset(canvasWidth, radius),
//                style = Stroke(width = 100f),
//                size = Size(radius, radius)
//            )
//            radius += radiusIncrementEach + 100f
//        }

//        radius = 600f
//        for (item in list[1]) {
//            drawArc(
//                color = item,
//                startAngle = 2 * degreeEach,
//                sweepAngle = 2 * degreeEach,
//                useCenter = false,
//                topLeft = offset(canvasWidth, radius),
//                style = Stroke(width = 100f),
//                size = Size(radius, radius)
//            )
//            radius += radiusIncrementEach + 100f
//        }
//
//        radius = 600f
//        for (item in list[2]) {
//            drawArc(
//                color = item,
//                startAngle = 4 * degreeEach,
//                sweepAngle = 2 * degreeEach,
//                useCenter = false,
//                topLeft = offset(canvasWidth, radius),
//                style = Stroke(width = 100f),
//                size = Size(radius, radius)
//            )
//            radius += radiusIncrementEach + 100f
//        }

//        radius = 600f
//        for (item in list[3]) {
//            drawArc(
//                color = item,
//                startAngle = 6 * degreeEach,
//                sweepAngle = 2 * degreeEach,
//                useCenter = false,
//                topLeft = offset(canvasWidth, radius),
//                style = Stroke(width = 100f),
//                size = Size(radius, radius)
//            )
//            radius += radiusIncrementEach + 100f
//        }
//
//        drawArc(
//            color = Color(0xFF87DD77),
//            startAngle = 0F,
//            sweepAngle = 360f,
//            useCenter = false,
//            topLeft = offset(canvasWidth, 500f),
//            style = Stroke(width = 100f),
//            size = Size(500f, 500f)
//        )
//
//        drawArc(
//            color = Color(0xFF5F7ECC),
//            startAngle = 0F,
//            sweepAngle = 360f,
//            useCenter = false,
//            topLeft = offset(canvasWidth, 700f),
//            style = Stroke(width = 100f),
//            size = Size(700f, 700f)
//        )
//
//        drawArc(
//            color = Color(0xFF8C5FCC),
//            startAngle = 0F,
//            sweepAngle = degreeEach,
//            useCenter = false,
//            topLeft = offset(canvasWidth, 700f),
//            style = Stroke(width = 100f),
//            size = Size(700f, 700f)
//        )
}


@Composable
fun ColorBox() {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawRect(
            color = Green,
            style = Stroke(50f)
        )
    })

}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun PreviewPalette() {
    Palette(defaultColor = Blue, modifier = Modifier.fillMaxSize(), list = Presets.custom())
}