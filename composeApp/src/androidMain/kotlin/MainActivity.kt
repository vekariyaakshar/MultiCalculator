package org.multicalculator.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

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
    val displayTextState = mutableStateOf("0")

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.LightGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.LightGray)
        ) {
            Row {
                CalcDisplay(displayTextState)
            }
            Row {
                Column {
                    CalcOperationButton("+", displayTextState)
                    CalcOperationButton("-", displayTextState)
                }
                Column {
                    CalcOperationButton("*", displayTextState)
                    CalcOperationButton("/", displayTextState)
                }
            }
            for (i in 7 downTo 1 step 3) {
                CalcRow(displayTextState, i, 3)
            }
            Row {
                CalcNumericButton(0, displayTextState)
                CalcEqualsButton(displayTextState)
            }
        }
    }
}

@Composable
fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        for (i in startNum until endNum) {
            CalcNumericButton(i, display)
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(
        text = display.value,
        modifier = Modifier
            .height(50.dp)
            .padding(5.dp)
            .fillMaxWidth()
    )
}
@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>) {
    Surface(
        color = Color.White
    ) {
        TextButton(
            onClick = { display.value += number.toString() },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(number.toString())
        }
    }
}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>) {
    Surface(
        color = Color.White
    ) {
        TextButton(
            onClick = { },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(operation)
        }
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>) {
    Surface(
        color = Color.White
    ) {
        TextButton(
            onClick = { display.value = "0" },
            modifier = Modifier.padding(4.dp)
        ) {
            Text("=")
        }
    }
}

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun App() {
    CalcView()
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
