package org.multi.calculator.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

class CalculatorViewModel : ViewModel() {
    val displayTextState: MutableState<String> = mutableStateOf("0")
    var operand1: Double? by mutableStateOf(null)
    var operand2: Double? by mutableStateOf(null)
    var operation: String? by mutableStateOf(null)
}

@Composable
fun CalculatorApp() {
    val viewModel: CalculatorViewModel = viewModel()
    CalculatorContent(viewModel)
}

@Composable
fun CalculatorContent(viewModel: CalculatorViewModel) {
    var leftNumber by rememberSaveable { mutableStateOf(0.0) }
    var rightNumber by rememberSaveable { mutableStateOf(0.0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }

    // Define the numberPress function with btnNum parameter
    val numberPress: (Int) -> Unit = { btnNum ->
        if (complete) {
            // Reset variables when complete is true
            leftNumber = 0.0
            rightNumber = 0.0
            operation = ""
            complete = false
            viewModel.displayTextState.value = btnNum.toString()
        } else {
            if (operation.isNotEmpty() && !complete) {
                rightNumber = (rightNumber * 10) + btnNum
                viewModel.displayTextState.value = rightNumber.toString()
            } else if (operation.isBlank() && !complete) {
                leftNumber = (leftNumber * 10) + btnNum
                viewModel.displayTextState.value = leftNumber.toString()
            }
        }
    }

    // Define the empty functions
    val operationPress: (String) -> Unit = { op ->
        if (!complete) {
            operation = op
        }
    }

    val equalsPress: () -> Unit = {
        complete = true
    }

    if (complete && operation.isNotEmpty()) {
        var answer = 0.0

        when (operation) {
            "+" -> answer = leftNumber + rightNumber
            "-" -> answer = leftNumber - rightNumber
            "*" -> answer = leftNumber * rightNumber
            "/" -> answer = if (rightNumber != 0.0) leftNumber / rightNumber else Double.NaN
        }

        viewModel.displayTextState.value = answer.toString()
        complete = false
        operation = ""
    } else if (operation.isNotEmpty() && !complete) {
        rightNumber = viewModel.displayTextState.value.toDoubleOrNull() ?: 0.0
        viewModel.displayTextState.value = rightNumber.toString()
    } else {
        leftNumber = viewModel.displayTextState.value.toDoubleOrNull() ?: 0.0
        viewModel.displayTextState.value = leftNumber.toString()
    }

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
                CalcDisplay(viewModel.displayTextState)
            }
            Row {
                Column {
                    CalcOperationButton("+", viewModel, operationPress)
                    CalcOperationButton("-", viewModel, operationPress)
                }
                Column {
                    CalcOperationButton("*", viewModel, operationPress)
                    CalcOperationButton("/", viewModel, operationPress)
                }
            }
            for (i in 7 downTo 1 step 3) {
                CalcRow(viewModel, i, 3) { number ->
                    numberPress(number)
                }
            }
            Row {
                CalcNumericButton(0, viewModel) { number ->
                    numberPress(number)
                }
                CalcEqualsButton(viewModel) {
                    equalsPress()
                }
            }
        }
    }
}

@Composable
fun CalcRow(viewModel: CalculatorViewModel, startNum: Int, numButtons: Int, onPress: (Int) -> Unit) {
    val endNum = startNum + numButtons

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        for (i in startNum until endNum) {
            CalcNumericButton(i, viewModel, onPress)
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
fun CalcNumericButton(number: Int, viewModel: CalculatorViewModel, onPress: (Int) -> Unit) {
    Surface(
        color = Color.White
    ) {
        TextButton(
            onClick = {
                onPress(number)
            },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(number.toString())
        }
    }
}

@Composable
fun CalcOperationButton(operation: String, viewModel: CalculatorViewModel, onPress: (String) -> Unit) {
    Surface(
        color = Color.White
    ) {
        TextButton(
            onClick = {
                viewModel.operand1 = viewModel.displayTextState.value.toDoubleOrNull()
                onPress(operation)
                viewModel.displayTextState.value = "0"
            },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(operation)
        }
    }
}

@Composable
fun CalcEqualsButton(viewModel: CalculatorViewModel, onPress: () -> Unit) {
    Surface(
        color = Color.White
    ) {
        TextButton(
            onClick = {
                viewModel.operand2 = viewModel.displayTextState.value.toDoubleOrNull()
                onPress()
                val result = when (viewModel.operation) {
                    "+" -> viewModel.operand1?.plus(viewModel.operand2 ?: 0.0)
                    "-" -> viewModel.operand1?.minus(viewModel.operand2 ?: 0.0)
                    "*" -> viewModel.operand1?.times(viewModel.operand2 ?: 0.0)
                    "/" -> if (viewModel.operand2 != 0.0) viewModel.operand1?.div(viewModel.operand2 ?: 0.0) else "Error"
                    else -> viewModel.operand1
                }
                viewModel.displayTextState.value = result.toString()
            },
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

@Preview
@Composable
fun AppAndroidPreview() {
    CalculatorApp()
}
