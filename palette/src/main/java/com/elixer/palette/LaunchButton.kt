package com.elixer.palette

import android.graphics.drawable.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LaunchButton(
    animationState: Boolean,
    onToggleAnimationState: () -> Unit,
    selectedColor: State<Color>,
    modifier: Modifier = Modifier.size(80.dp)
) {

//    val animatedColor = animateColorAsState(if (animationState) Color.Black else Color.LightGray)
//    val animatedColor = animateColorAsState(selectedColor)
    val text = if (animationState) "Fold" else "Unfold"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {

        FloatingActionButton(
            onClick = onToggleAnimationState,
            backgroundColor = selectedColor.value,
            contentColor = Color.White,
            modifier = modifier
        ) {
            Text(text)
        }
    }
}