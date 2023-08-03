package com.hafihaf.calcul_ovator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hafihaf.calcul_ovator.ui.theme.CalculovatorTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Equals

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculovatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val expressionState = remember { mutableStateOf(TextFieldValue("")) }
    val resultState = remember { mutableStateOf(0.0) }

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputTextField(focusRequester = focusRequester,
            text = expressionState.value,
            onTextChange =  { expressionValue -> expressionState.value = expressionValue },
            onCalculate = { calculatedResult ->  resultState.value = calculatedResult }
        )

        Output(out = resultState.value.toString())
    }

    CalculateButton(text = expressionState.value,
        onCalculate = { calculatedResult -> resultState.value = calculatedResult }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CalculovatorTheme {
        MainScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputTextField(
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    onCalculate: (Double) -> Unit
) {
    OutlinedTextField(
        value = text,
        label = { Text(text = "Enter an expression") },
        onValueChange = { onTextChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (text == TextFieldValue("")) onCalculate(0.0)
                else {
                    val result = Expression.eval(text.text)
                    // Update the state with the calculated result
                    onCalculate(result)
                }
            }
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester)
    )
}

@Composable
private fun CalculateButton(
    text: TextFieldValue,
    onCalculate: (Double) -> Unit
) {
    FloatingActionButton(
        onClick = {
            if (text == TextFieldValue("")) onCalculate(0.0)
            else {
                val result = Expression.eval(text.text)
                // Update the state with the calculated result
                onCalculate(result)
            }
        },
        modifier = Modifier
            .absoluteOffset(310.dp, 700.dp)
            .size(63.dp, 130.dp)
    ) {
        Icon(
            imageVector = FontAwesomeIcons.Solid.Equals,
            contentDescription = "calculate",
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
        )
    }
}

/*
@Composable
private fun FakeKeyboardButton(
    whenClicked: String,
    iconImage: ImageVector
) {
    FloatingActionButton(
        onClick = { },
        modifier = Modifier
            .absoluteOffset(10.dp, 7.dp)
            .size(63.dp, 75.dp)
    ) {
        Icon(
            imageVector = FontAwesomeIcons.Solid.Equals,
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
        )
    }
}
*/

@Composable
private fun Output(out: String){
    if (out == "0.0") { return }
    else Text(text = out)
}