package com.hafihaf.calcul_ovator.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp


@Composable
fun Output(out: String){
    Text(
        text = out,
        fontSize = 20.sp
    )
}