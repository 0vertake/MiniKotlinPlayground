package com.milos.minikotlinplayground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.milos.minikotlinplayground.executor.ExecutionResult
import com.milos.minikotlinplayground.executor.ScriptExecutor
import com.milos.minikotlinplayground.executor.ExecutionState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import minikotlinplayground.composeapp.generated.resources.Res
import minikotlinplayground.composeapp.generated.resources.kotlin_icon

@Composable
@Preview
fun App() {
    val executor = remember { ScriptExecutor() }
    val scope = rememberCoroutineScope()

    var state by remember {
        mutableStateOf(
            ExecutionState(
                scriptContent = "for (i in 1..5) {\n" +
                        "    println(\"Count \$i.\")\n" +
                        "}",
                output = "Output will appear here...",
                isRunning = false,
                exitCode = null
            )
        )
    }

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    MaterialTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Left panel - Script
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.kotlin_icon),
                                contentDescription = "Kotlin",
                                modifier = Modifier.width(24.dp).height(24.dp),
                                tint = Color.Unspecified
                            )
                            Text(
                                text = "Kotlin Script",
                                style = typography.titleLarge,
                                color = colors.onSurfaceVariant
                            )
                        }
                        Button(
                            onClick = {
                                state = state.copy(isRunning = true, output = "", exitCode = null)
                                scope.launch {
                                    executor.executeScript(state.scriptContent).collect { result ->
                                        when (result) {
                                            is ExecutionResult.Output -> {
                                                state = state.copy(output = state.output + result.line + "\n")
                                            }
                                            is ExecutionResult.Error -> {
                                                state = state.copy(output = state.output + "ERROR: ${result.message}\n")
                                            }
                                            is ExecutionResult.Finished -> {
                                                state = state.copy(exitCode = result.exitCode, isRunning = false)
                                            }
                                        }
                                    }
                                }
                            },
                            enabled = !state.isRunning,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.primary,
                                contentColor = colors.onPrimary
                            )
                        ) {
                            Text(text = "▶ Run", style = typography.labelLarge)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colors.surface
                        )
                    ) {
                        TextField(
                            value = state.scriptContent,
                            onValueChange = { state = state.copy(scriptContent = it) },
                            modifier = Modifier.fillMaxSize(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colors.surface,
                                unfocusedContainerColor = colors.surface,
                                focusedTextColor = colors.onSurface,
                                unfocusedTextColor = colors.onSurface,
                                cursorColor = colors.primary,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                            singleLine = false
                        )
                    }
                }
            }

            // Separator
            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.width(16.dp)
            )

            // Right panel - Output
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Output",
                        style = typography.titleLarge,
                        color = colors.onSurfaceVariant
                    )

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colors.surface
                        )
                    ) {
                        TextField(
                            value = state.output,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxSize(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colors.surface,
                                unfocusedContainerColor = colors.surface,
                                disabledContainerColor = colors.surface,
                                focusedTextColor = colors.onSurface,
                                unfocusedTextColor = colors.onSurface,
                                disabledTextColor = colors.onSurface,
                                cursorColor = colors.primary,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            textStyle = typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                            singleLine = false
                        )
                    }
                }
            }
        }
    }
}