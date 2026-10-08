package org.example.simpleandroidcalc

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "simple-android-calc") {
        App()
    }
}
