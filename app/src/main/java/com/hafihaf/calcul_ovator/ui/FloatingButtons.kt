package com.hafihaf.calcul_ovator.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hafihaf.calcul_ovator.R

@Composable
fun IconFloatingButton(
    whenClicked: () -> Unit,
    iconImage: ImageVector,
) {
    FloatingActionButton(
        onClick = whenClicked,
    ) {
        Icon(
            imageVector = iconImage,
            contentDescription = null,
            modifier = Modifier.size(30.dp),
        )
    }
}

val awesomeFont = FontFamily(
    Font(R.font.fa_solid_900)
)

@Composable
fun TextFloatingButton(
    whenClicked: () -> Unit,
    text: String,
) {
    FloatingActionButton(
        onClick = whenClicked,
    ) {
        Text(
            text = text,
            fontFamily = awesomeFont,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 35.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}