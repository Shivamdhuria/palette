package com.elixer.palette.surface

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elixer.palette.shape.ArchShape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ColorButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape ,
    backgroundColor: Color,
    contentColor: Color = Color.White,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor.copy(alpha = 1f),
        elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
        onClick = onClick,
        enabled = enabled,
        role = Role.Button,
        interactionSource = interactionSource,
        indication = rememberRipple()
    ) {

    }
}
@Preview(showBackground = true, widthDp = 500, heightDp = 900)
@Composable
fun Crazy() {
    ColorButton(
        { }, modifier = Modifier.size(200.dp, 100.dp),
        shape = ArchShape(400f, 200f, 0f, 360f),
        backgroundColor = Color.Green

    ) {

    }
    ColorButton(
        { }, modifier = Modifier.size(700.dp, 600.dp).offset(0.dp,0.dp),
        shape = ArchShape(500f, 200f, 0f, 360f),
        backgroundColor = Color.Red

    ) {

    }
}