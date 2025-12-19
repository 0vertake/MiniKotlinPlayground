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
        try {
            tempFile.writeText(scriptContent)

            val command = if (isWindows) {
                listOf("cmd", "/c", "kotlinc", "-script", tempFile.absolutePath)
            } else {
                listOf("/usr/bin/env", "kotlinc", "-script", tempFile.absolutePath)
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

        } catch (e: Exception) {
            val message = if (e.message?.contains("Cannot run program") == true) {
                "Kotlin compiler not found. Please install Kotlin:\n" +
                        "  • Windows: scoop install kotlin  OR  choco install kotlin\n" +
                        "  • macOS: brew install kotlin\n" +
                        "  • Linux: sdk install kotlin"
            } else {
                e.message ?: "Unknown error"
            }
            emit(ExecutionResult.Error(message))
        } finally {
            tempFile.delete()
        }
    }.flowOn(Dispatchers.IO)
}