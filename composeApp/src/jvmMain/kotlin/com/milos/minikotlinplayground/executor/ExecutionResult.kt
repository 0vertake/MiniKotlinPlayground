package com.milos.minikotlinplayground.executor

sealed class ExecutionResult {
    data class Output(val line: String) : ExecutionResult()
    data class Error(val message: String) : ExecutionResult()
    data class Finished(val exitCode: Int) : ExecutionResult()
}
