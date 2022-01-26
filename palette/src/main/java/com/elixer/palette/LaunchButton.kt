package com.elixer.palette

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LaunchButton(
    animationState: Boolean,
    onToggleAnimationState: () -> Unit,
    selectedColor: State<Color>,
    offsetX: Dp,
    offsetY: Dp,
    buttonSize:Dp
) {

//    val animatedColor = animateColorAsState(if (animationState) Color.Black else Color.LightGray)
//    val animatedColor = animateColorAsState(selectedColor)
    val text = if (animationState) "Fold" else "Unfold"

    FloatingActionButton(
        onClick = onToggleAnimationState,
        backgroundColor = selectedColor.value,
        contentColor = Color.White,
        modifier = Modifier.size(buttonSize).offset(
            offsetX-buttonSize/2,offsetY-buttonSize/2
        )
    ) {}
}