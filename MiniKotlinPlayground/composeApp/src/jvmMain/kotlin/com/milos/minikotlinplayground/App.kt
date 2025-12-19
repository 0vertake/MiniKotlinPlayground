package com.milos.minikotlinplayground

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.milos.minikotlinplayground.executor.ExecutionResult
import com.milos.minikotlinplayground.executor.ExecutionState
import com.milos.minikotlinplayground.executor.ScriptExecutor
import com.milos.minikotlinplayground.ui.EditorPane
import com.milos.minikotlinplayground.ui.OutputPane
import com.milos.minikotlinplayground.ui.StatusBar
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val executor = remember { ScriptExecutor() }
    val scope = rememberCoroutineScope()

    var state by remember {
        mutableStateOf(
            ExecutionState(
                scriptContent = "import java.lang.Thread.sleep\n" +
                        "for (i in 1..5) {\n" +
                        "    sleep(1000)\n" +
                        $$"    println(\"App is running... $i\")\n" +
                        "}",
                isRunning = false,
                exitCode = null
            )
        )
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
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
                    onRunClick = {
                        state = state.copy(isRunning = true, output = "", exitCode = null)
                        scope.launch {
                            executor.executeScript(state.scriptContent).collect { result ->
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
                        .fillMaxSize()
                )
            }

            StatusBar(
                isRunning = state.isRunning,
                exitCode = state.exitCode
            )
        }
    }
}
