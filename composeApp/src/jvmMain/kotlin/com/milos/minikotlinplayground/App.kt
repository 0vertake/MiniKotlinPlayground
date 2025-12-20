package com.milos.minikotlinplayground

import com.milos.minikotlinplayground.executor.ExecutionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.milos.minikotlinplayground.executor.ExecutionResult
import com.milos.minikotlinplayground.executor.ScriptExecutor
import com.milos.minikotlinplayground.ui.EditorPane
import com.milos.minikotlinplayground.ui.OutputPane
import com.milos.minikotlinplayground.ui.StatusBar
import com.milos.minikotlinplayground.util.lineColumnToPosition
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val executor = remember { ScriptExecutor() }
    val scope = rememberCoroutineScope()

    var errorClickCounter by remember { mutableIntStateOf(0) }

    var state by remember {
        mutableStateOf(
            ExecutionState(
                scriptContent = TextFieldValue(
                    text = "import java.lang.Thread.sleep\n" +
                            "for (i in 1..5) {\n" +
                            "    sleep(1000)\n" +
                            "    println(\"App is running... \$i\")\n" +
                            "}"
                ),
                isRunning = false,
                exitCode = null
            )
        )
    }

    val kotlinGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF7F52FF).copy(alpha = 0.8f),
            Color(0xFFE1306C).copy(alpha = 0.7f)
        )
    )

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(kotlinGradient)
                .safeContentPadding()
        ) {
            StatusBar(
                isRunning = state.isRunning,
                exitCode = state.exitCode,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                EditorPane(
                    scriptContent = state.scriptContent,
                    isRunning = state.isRunning,
                    onScriptChange = { state = state.copy(scriptContent = it) },
                    errorClickTrigger = errorClickCounter,
                    onRunClick = {
                        state = state.copy(isRunning = true, output = "", exitCode = null)
                        scope.launch {
                            executor.executeScript(state.scriptContent.text).collect { result ->
                                state = when (result) {
                                    is ExecutionResult.Output -> {
                                        state.copy(output = state.output + result.line + "\n")
                                    }

                                    is ExecutionResult.Error -> {
                                        state.copy(output = state.output + "ERROR: ${result.message}\n")
                                    }

                                    is ExecutionResult.Finished -> {
                                        state.copy(exitCode = result.exitCode, isRunning = false)
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutputPane(
                    output = state.output,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    onErrorLocationClick = { line, column ->
                        val position = lineColumnToPosition(state.scriptContent, line, column)
                        state = state.copy(
                            scriptContent = state.scriptContent.copy(
                                selection = TextRange(position)
                            )
                        )
                        errorClickCounter++
                    }
                )
            }
        }
    }
}
