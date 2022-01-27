package com.elixer.colorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import com.elixer.palette.composables.Palette
import com.elixer.palette.Presets
import com.elixer.palette.constraints.HorizontalAlignment.*
import com.elixer.palette.constraints.VerticalAlignment.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1E1E1F))

                    ) {
                        Palette(
                            defaultColor = Color(0xFFFA8A4D),
                            buttonSize = 190.dp,
                            list = Presets.custom(),
                            innerRadius = 1040f,
                            strokeWidth = 120f,
                            spacerRotation = 1f,
                            spacerOutward = 0f,
                            verticalAlignment = Top,
                            horizontalAlignment = Start
                        )
//
//                        Palette(
//                            Color(0xFF65CCCC),
//                            buttonSize = 100.dp,
//                            Presets.custom(),
//                            verticalAlignment = Top,
//                            horizontalAlignment = Center
//                        )
//
//                        Palette(
//                            Color(0xFF657BCC), buttonSize = 100.dp,
//                            Presets.custom(),
//                            verticalAlignment = Top,
//                            horizontalAlignment = End
//                        )
//
//                        //mid
//                        Palette(
//                            Color(0xFF8465CC),
//                            buttonSize = 100.dp,
//                            Presets.custom(),
//                            verticalAlignment = Middle,
//                            horizontalAlignment = Start
//                        )
//
//                        Palette(
//                            Color(0xFFCC65C2),
//                            buttonSize = 100.dp,
//                            Presets.custom(),
//                            verticalAlignment = Middle,
//                            horizontalAlignment = Center
//                        )
//
//                        Palette(
//                            Color(0xFF8465CC),
//                            buttonSize = 100.dp,
//                            Presets.custom(),
//                            verticalAlignment = Middle,
//                            horizontalAlignment = End
//                        )
//
//                        //end
//                        Palette(
//                            Color(0xFFCC6565),
//                            buttonSize = 100.dp,
//                            Presets.custom(),
//                            verticalAlignment = Bottom,
//                            horizontalAlignment = Start
//                        )
//
//                        Palette(
//                            Color(0xFFCC9265),
//                            buttonSize = 100.dp,
//                            Presets.custom(),
//                            verticalAlignment = Bottom,
//                            horizontalAlignment = Center
//                        )
//
//                        Palette(
//                            Color(0xFFC2CC65),
//                            buttonSize = 100.dp,
//                            Presets.custom(),
//                            verticalAlignment = Bottom,
//                            horizontalAlignment = End
//                        )


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
        Text(text = "palette", fontSize = 80.sp, color = Color.White, textAlign = TextAlign.Center)
        Text(text = "color picker made with Jetpack Compose", fontSize = 15.sp, color = Color.LightGray, textAlign = TextAlign.Center)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ColorAppTheme {
        Greeting("Android")
    }
}