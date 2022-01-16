package com.elixer.colorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.elixer.colorapp.ui.theme.ColorAppTheme
import com.elixer.palette.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
//                        Palette(
//                            Color.Green, buttonSize = 80.dp,
//                            Presets.custom(),
//                            modifier = Modifier
//                                .offset(100.dp,100.dp)
//
//                        )

                        Column {
//                            Heading()
                            NewPalette(
                                size = 300.dp,
                                modifier = Modifier
                                    .offset(30.dp, 40.dp)
                            )

                            NewNewPalette(
                                Color.Green, buttonSize = 90.dp,
                                Presets.custom(),
                                modifier = Modifier
                                    .size(300.dp)
                                    .offset(0.dp, 90.dp)

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Heading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "palette", fontSize = 80.sp, color = Color.DarkGray, textAlign = TextAlign.Center)
        Text(text = "color picker made with Jetpack Compose", fontSize = 15.sp, color = Color.Gray, textAlign = TextAlign.Center)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    ColorSwatches(list = listOf(Color.Blue, Color.Black))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ColorAppTheme {
        Greeting("Android")
    }
}