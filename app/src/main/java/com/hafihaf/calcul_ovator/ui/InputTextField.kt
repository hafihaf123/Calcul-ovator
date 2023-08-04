package com.hafihaf.calcul_ovator.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hafihaf.calcul_ovator.Expression


@Composable
fun InputTextField(
    text: String,
    onTextChange: (String) -> Unit,
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
                if (text == "") onCalculate(0.0)
                else {
                    val result = Expression.eval(text)
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