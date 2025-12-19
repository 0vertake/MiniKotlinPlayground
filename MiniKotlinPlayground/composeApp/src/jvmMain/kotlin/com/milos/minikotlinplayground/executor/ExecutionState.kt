package com.milos.minikotlinplayground.executor

data class ExecutionState(
    val scriptContent: String = "",
    val output: String = "",
    val isRunning: Boolean = false,
    val exitCode: Int? = null
)