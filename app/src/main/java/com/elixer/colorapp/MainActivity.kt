package com.elixer.colorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elixer.colorapp.ui.theme.ColorAppTheme
import com.elixer.palette.*
import com.elixer.palette.newSh.ColorSingleNew
import com.elixer.palette.newSh.NewPalette

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //New


            ColorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

                        NewPalette(
                            size =200.dp,
                            modifier = Modifier
                                .offset(40.dp, 180.dp)
                        )
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