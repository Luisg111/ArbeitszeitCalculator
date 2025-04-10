package de.luisg.myapplication.ui.theme


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ArbeitszeitCalculatorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content, colors = MaterialTheme.colors.copy(
            primary = Color(70, 120, 255)

        )
    )
}