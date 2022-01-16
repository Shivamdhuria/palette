package com.elixer.palette

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.elixer.palette.shape.ArchShape
import com.elixer.palette.shape.CustomShape
import kotlinx.coroutines.launch

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

            }.clipToBounds()
    ) {

        LaunchButton(
            animationState = animationState.value,
            onToggleAnimationState = { animationState.value = !animationState.value }
        )

        ColorSingle(
            isColorVisible = animationState.value,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            outerRadius = 410f,
            innerRadius = 290f,
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
    outerRadius: Float,
    innerRadius: Float,
) {


    Log.e("maxWid $maxWidth","max He $maxHeight")
    Log.e("outer $outerRadius","inner $innerRadius")
    fun arcOffset(width: Float, size: Float): Offset =
        Offset(width / 2f - size, width / 2f - size)


    val nOffset = arcOffset(maxWidth.value,outerRadius)
//    val x = maxHeight.value.dp/2
    val x = 0.dp
    val radius = outerRadius
    Log.e("x"," $x")
    Log.e("radius F"," $radius")
    Log.e("radius Dp"," ${radius.dp}")

    ArchedButton(
        onClick = { },
        modifier = Modifier.aspectRatio(1f)
            .offset(x,x ),
        shape = ArchShape(outerRadius, innerRadius, 0f, 360f)
    ) {

    }
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