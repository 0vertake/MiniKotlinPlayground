package com.milos.minikotlinplayground

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.isTraySupported
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import minikotlinplayground.composeapp.generated.resources.Res
import minikotlinplayground.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.skia.paragraph.TextBox

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Enter text here") }
    MaterialTheme {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                onClick = { println("Click") }
            ) {
                Text(
                    text = "Click me",
                )
            }
        }
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it},
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground)
                    .safeContentPadding()
                    .padding(10.dp)
            )
            TextField(
                value = text,
                onValueChange = { text = it},
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground)
                    .safeContentPadding()
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
    }
}