package com.hafihaf.calcul_ovator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hafihaf.calcul_ovator.Expression
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Backspace
import compose.icons.fontawesomeicons.solid.Divide
import compose.icons.fontawesomeicons.solid.Equals
import compose.icons.fontawesomeicons.solid.Times

@Composable
fun FakeKeyboard(
    text: String,
    textFieldValue: (String) -> Unit,
    onCalculate: (Double) -> Unit
) {
    var textFieldResult = text
    Row (
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            TextFloatingButton(
                whenClicked = {
                    textFieldResult = ""
                    textFieldValue(textFieldResult)
                    onCalculate(0.0)
                    },
                text = "C",
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "7"
                    textFieldValue(textFieldResult)
                    },
                text = "7"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "4"
                    textFieldValue(textFieldResult)
                    },
                text = "4"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "1"
                    textFieldValue(textFieldResult)
                    },
                text = "1"
            )
        }

        Column (
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            IconFloatingButton(
                whenClicked = {
                    textFieldResult += "/"
                    textFieldValue(textFieldResult)
                    },
                iconImage = FontAwesomeIcons.Solid.Divide
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "8"
                    textFieldValue(textFieldResult)
                    },
                text = "8"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "5"
                    textFieldValue(textFieldResult)
                    },
                text = "5"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "2"
                    textFieldValue(textFieldResult)
                    },
                text = "2"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "0"
                    textFieldValue(textFieldResult)
                    },
                text = "0"
            )
        }

        Column (
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            IconFloatingButton(
                whenClicked = {
                    textFieldResult += "*"
                    textFieldValue(textFieldResult)
                    },
                iconImage = FontAwesomeIcons.Solid.Times
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "9"
                    textFieldValue(textFieldResult)
                    },
                text = "9"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "6"
                    textFieldValue(textFieldResult)
                    },
                text = "6"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "3"
                    textFieldValue(textFieldResult)
                    },
                text = "3"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "."
                    textFieldValue(textFieldResult)
                    },
                text = "."
            )
        }

        Column (
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            IconFloatingButton(
                whenClicked = {
                    textFieldResult = textFieldResult.dropLast(1)
                    textFieldValue(textFieldResult)
                    },
                iconImage = FontAwesomeIcons.Solid.Backspace
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "-"
                    textFieldValue(textFieldResult)
                    },
                text = "—"
            )
            TextFloatingButton(
                whenClicked = {
                    textFieldResult += "+"
                    textFieldValue(textFieldResult)
                    },
                text = "+"
            )

            FloatingActionButton(
                onClick = {
                    if (text == "") onCalculate(0.0)
                    else {
                        val result = Expression.eval(text)
                        // Update the state with the calculated result
                        onCalculate(result)
                    }
                },
                modifier = Modifier.size(69.dp, 154.dp)
            ) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Equals,
                    contentDescription = "calculate",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}