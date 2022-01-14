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
//                val windowBounds = it.boundsInWindow()
                Log.e("windowBounds ${it.size.toString()}", "")
                Log.e("rdinates ${it.parentLayoutCoordinates.toString()}", "")
                Log.e("width ${it.size.width.toString()}", "")


//                    composableSize.value = it.size.toSize()
                centerX = it.size.width / 2f
                centerY = it.size.height / 2f
                Log.e("centerX $centerX", "center Y $centerY")
                Log.e("centerX ${centerX.dp}", "center Y ${centerY.dp}")
            }
    ) {

        LaunchButton(
            animationState = animationState.value,
            onToggleAnimationState = { animationState.value = !animationState.value }
        )

        Color(
            isColorVisible = animationState.value,
            maxWidth = maxWidth,
            maxHeight = maxHeight
        )

//        FloatingActionButton(
//            modifier = Modifier.align(Alignment.Center),
//            onClick = {
//                scope.launch {
//                    if (imageSize == null || containerSize == null) return@launch
//                    val targetSize = if (animatableSize.value == imageSize) containerSize else imageSize
//                    animatableSize.animateTo(
//                        targetSize,
//                        animationSpec = tween(durationMillis = 1000)
//                    )
//                }
//            },
//            backgroundColor = Color.Black,
//            contentColor = Color.White,
//        ) {
//            Icon(Icons.Filled.Add, "", modifier = Modifier.size(40.dp))
//        }



        Image(
            Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier
                .then(
                    if (animatableSize.value != Size.Zero) {
                        animatableSize.value.run {
                            Modifier.size(
                                width = with(density) { width.toDp() },
                                height = with(density) { height.toDp() },
                            )
                        }
                    } else {
                        Modifier
                    }
                )
                .onSizeChanged { intSize ->
                    if (imageSize != null) return@onSizeChanged
                    val size = intSize.toSize()
                    setImageSize(size)
                    scope.launch {
                        animatableSize.snapTo(size)
                    }
                }
        )
    }
}

@Composable
fun Color(isColorVisible: Boolean, maxWidth: Dp, maxHeight: Dp) {
    val offsetY = remember { Animatable(0f) }
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val offsetV: Offset by animateOffsetAsState(Offset(if (isColorVisible) 50f else 0f, if (isColorVisible) 50f else 0f))


    Button(
        onClick = {}, modifier = Modifier
            .height(50.dp)
            .aspectRatio(1f)
            .offset(offsetV.x.dp,offsetV.y.dp),
        shape = CustomShape(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
    )
    {

    }
}


@Composable
private fun ColorPictureButton(defaultColor: Color) {
    FloatingActionButton(
        onClick = {},
        backgroundColor = defaultColor,
        contentColor = Color.White
    ) {
        Icon(Icons.Filled.Add, "", modifier = Modifier.size(80.dp))
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun NewreviewPalette() {
    NewPalette(
        size = 200.dp,
        modifier = Modifier
            .offset(20.dp, 50.dp)
    )
}