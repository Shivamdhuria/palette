package com.elixer.colorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elixer.colorapp.ui.theme.ColorAppTheme
import com.elixer.palette.ColorSwatches
import com.elixer.palette.Palette
import com.elixer.palette.Presets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Palette(
                            Color.Green, buttonSize = 80.dp,
                            Presets.custom(),
                            modifier = Modifier
                                .height(450.dp)
                                .width(450.dp)
                                .offset(-50.dp,200.dp)
                        )
                        Column {
                            Heading()
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