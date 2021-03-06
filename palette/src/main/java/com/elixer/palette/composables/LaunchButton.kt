package com.elixer.palette.composables

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun LaunchButton(
    onToggleAnimationState: () -> Unit,
    selectedColor: Color,
    offsetX: Dp,
    offsetY: Dp,
    buttonSize:Dp
) {

    FloatingActionButton(
        onClick = onToggleAnimationState,
        backgroundColor = selectedColor,
        contentColor = Color.White,
        modifier = Modifier.size(buttonSize).offset(
            offsetX-buttonSize/2,offsetY-buttonSize/2
        )
    ) {}
}