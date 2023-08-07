package com.hafihaf.calcul_ovator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hafihaf.calcul_ovator.ui.theme.CalculovatorTheme

@Composable
fun FakeKeyboardCommand(
//    text: String,
//    textFieldValue: (String) -> Unit,
//    onCalculate: (Double) -> Unit
) {
//    var textFieldResult = text
    Column (
        modifier = Modifier
            .padding(40.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TextFloatingButton(
            whenClicked = {
            },
            text = "convert",
        )

        TextFloatingButton(
            whenClicked = {
            },
            text = "bodies"
        )

        TextFloatingButton(
            whenClicked = {
            },
            text = "rule of 3"
        )

        TextFloatingButton(
            whenClicked = {
            },
            text = "quadratic"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FakeKeyboardCommandPreview() {
    CalculovatorTheme {
        FakeKeyboardCommand()
//            text = "",
//            { },
//            { }
//        )
    }
}