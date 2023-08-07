package com.hafihaf.calcul_ovator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hafihaf.calcul_ovator.ui.FakeKeyboardCommand

@Composable
fun CommandScreen(/*navController: NavController*/) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FakeKeyboardCommand()
//            text = expressionState,
//            { textFieldValue -> expressionState = textFieldValue },
//            { calculatedResult -> resultState.doubleValue = calculatedResult }
//        )
    }
}