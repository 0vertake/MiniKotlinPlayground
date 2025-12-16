package com.milos.minikotlinplayground

data class ExecutionState(
    val scriptContent: String = "",
    val output: String = "",
    val isRunning: Boolean = false,
    val exitCode: Int? = null
)
