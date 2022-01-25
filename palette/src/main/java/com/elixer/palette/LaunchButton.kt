package com.elixer.palette

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LaunchButton(
    animationState: Boolean,
    onToggleAnimationState: () -> Unit,
    selectedColor: State<Color>,
    modifier: Modifier
) {

//    val animatedColor = animateColorAsState(if (animationState) Color.Black else Color.LightGray)
//    val animatedColor = animateColorAsState(selectedColor)
    val text = if (animationState) "CCCC" else "OOOO"

//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .padding(8.dp),
//        contentAlignment = Alignment.Center
//    ) {

        FloatingActionButton(
            onClick = onToggleAnimationState,
            backgroundColor = selectedColor.value,
            contentColor = Color.White,
            modifier = modifier
        ) {
            Text(text)
        }
//    }
}