package org.multicalculator.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.os.Bundle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Composable
fun CalcView() {
    // Your UI code for the calculator view goes here
}

@Composable
fun CalcRow() {
    // Your UI code for a single row in the calculator view goes here
}

@Composable
fun CalcDisplay() {
    // Your UI code for the calculator display goes here
}

@Composable
fun CalcNumericButton() {
    // Your UI code for numeric buttons in the calculator goes here
}

@Composable
fun CalcOperationButton() {
    // Your UI code for operation buttons in the calculator goes here
}

@Composable
fun App() {
    // Your main app UI code goes here
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
