package com.milos.minikotlinplayground.executor

import androidx.compose.ui.text.input.TextFieldValue

data class ExecutionState(
    val scriptContent: TextFieldValue,
    val isRunning: Boolean,
    val output: String = "",
    val exitCode: Int? = null
)