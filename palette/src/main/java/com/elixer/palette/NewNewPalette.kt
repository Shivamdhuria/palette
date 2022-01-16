package com.elixer.palette

import android.graphics.Rect
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.elixer.palette.models.ColorBox
import com.elixer.palette.shape.ArchShape
import com.elixer.palette.surface.ColorButton
import kotlinx.coroutines.sync.Mutex

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewNewPalette(
    defaultColor: Color,
    buttonSize: Dp = 80.dp,
    list: List<List<Color>>,
    innerRadius: Float = 400f,
    colorLength: Float = 80f,
    modifier: Modifier,
    size: Dp = 300.dp
) {

    /**
     * Color boxes to draw
     */
    val particles = remember { mutableStateOf(emptyList<ColorBox>()) }


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
    val animateFloat1 = remember { Animatable(0f) }
    var touchX by remember { mutableStateOf(0f) }
    var touchY by remember { mutableStateOf(0f) }
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }

    val colorFirstRow = mutableListOf<Color>()
    list.forEach {
        colorFirstRow.add(it[0])
    }

//New stuff
    val animationState = remember { mutableStateOf(false) }


//    Box(modifier = modifier) {


//        ColorPictureButton(defaultColor)
//        ColorWheel(innerRadius, colorFirstRow, colorLength, modifier)

//    }

    BoxWithConstraints(
        modifier = modifier
            .width(size)
            .height(size)
            .background(Color.Red)
            .onGloballyPositioned { it ->
                centerX = it.size.width / 2f
                centerY = it.size.height / 2f
                Log.e("centerX $centerX", "centerY,$centerY")

            }
            .clipToBounds()
    ) {
        LaunchButton(
            animationState = animationState.value,
            onToggleAnimationState = { animationState.value = !animationState.value }
        )
        NewColorWheel(
            300f,
            colorFirstRow,
            colorLength,
            maxHeight
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


@Composable
fun NewColorWheel(
    innerRadius: Float,
    colorRow: List<Color>,
    colorLength: Float,
    maxHeight: Dp
) {

    Log.e("max heigt", maxHeight.toString())
    var startAngle = 0f
    val degreeEach = 360f / colorRow.size

    fun offset(width: Float, size: Float): Offset =
        Offset(width / 2f - size / 2f, width / 2f - size / 2f)

    ColorButton(
        onClick = { },
        shape = ArchShape(300f,200f,0f,360f),
        backgroundColor = colorRow[0]
    ) {

    }

    ColorButton(
        onClick = { },
        shape = ArchShape(300f,200f,0f,360f),
        backgroundColor = colorRow[0]
    ) {

    }
}


@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun PreviewNewNewPalette() {
    NewNewPalette(
        defaultColor = Blue,
        modifier = Modifier.size(400.dp, 400.dp),
        list = Presets.custom(),
        size = 100.dp
    )

}