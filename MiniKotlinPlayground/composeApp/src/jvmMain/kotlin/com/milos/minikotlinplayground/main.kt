package com.milos.minikotlinplayground

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MiniKotlinPlayground",

    ) {
        App()
    }
}