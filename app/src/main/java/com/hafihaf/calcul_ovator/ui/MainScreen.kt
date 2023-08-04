package com.hafihaf.calcul_ovator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hafihaf.calcul_ovator.ui.theme.CalculovatorTheme

@Composable
fun MainScreen() {
    var expressionState by remember { mutableStateOf("") }
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
            text = expressionState,
            onTextChange =  { expressionValue -> expressionState = expressionValue }
        ) { calculatedResult -> resultState.value = calculatedResult }

        Output(out = resultState.value.toString())

        Divider(
            modifier = Modifier
                .padding(15.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )

        FakeKeyboard(
            text = expressionState,
        { textFieldValue -> expressionState = textFieldValue },
        { calculatedResult -> resultState.value = calculatedResult }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CalculovatorTheme {
        MainScreen()
    }
}