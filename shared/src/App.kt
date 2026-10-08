package org.example.simpleandroidcalc

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.reload.DevelopmentEntryPoint

@Composable
@Preview
@DevelopmentEntryPoint
fun App() {
    MaterialTheme {
        CalculatorScreen()
    }
}
