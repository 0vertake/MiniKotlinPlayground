package com.milos.minikotlinplayground.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class ScriptExecutor {

    private val isWindows = System.getProperty("os.name").lowercase().contains("windows")

    fun executeScript(scriptContent: String): Flow<ExecutionResult> = flow {
        val tempFile = File.createTempFile("script", ".kts")
        tempFile.writeText(scriptContent)

        val command = when {
            isWindows -> listOf("cmd", "/c", "kotlinc", "-script", tempFile.absolutePath)
            else -> listOf("/usr/bin/env", "kotlinc", "-script", tempFile.absolutePath)
        }

        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .start()

        process.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                emit(ExecutionResult.Output(line))
            }
        }

        val exitCode = process.waitFor()
        emit(ExecutionResult.Finished(exitCode))
        tempFile.delete()

    }.flowOn(Dispatchers.IO)
}