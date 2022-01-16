package com.elixer.palette

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LaunchButton(
    animationState: Boolean,
    onToggleAnimationState: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
       contentAlignment = Alignment.Center
    ) {
        if (animationState) {
            FloatingActionButton(
                onClick = onToggleAnimationState,
                backgroundColor = Color.Black,
                contentColor = Color.White,
            ) {
                Text("Fold")
            }
        } else {
            FloatingActionButton(
                onClick = onToggleAnimationState,
                backgroundColor = Color.LightGray,
                contentColor = Color.White,
            ) {
                Text("Unfold")
            }
        }
    }
}