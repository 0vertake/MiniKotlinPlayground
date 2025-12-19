package com.milos.minikotlinplayground.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.milos.minikotlinplayground.syntax.SyntaxHighlighter
import minikotlinplayground.composeapp.generated.resources.Res
import minikotlinplayground.composeapp.generated.resources.kotlin_icon
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.rememberScrollbarAdapter

@Composable
fun EditorPane(
    scriptContent: TextFieldValue,
    isRunning: Boolean,
    onScriptChange: (TextFieldValue) -> Unit,
    onRunClick: () -> Unit,
    errorClickTrigger: Int = 0,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val highlighter = SyntaxHighlighter()
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()
    val lineCount = remember(scriptContent.text) { scriptContent.text.count { it == '\n' } + 1 }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(errorClickTrigger) {
        if (errorClickTrigger > 0) {
            focusRequester.requestFocus()
        }
    }

    Card(
        modifier = modifier,
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
                    onClick = onRunClick,
                    enabled = !isRunning,
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
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp)
                        ) {
                            repeat(lineCount) { index ->
                                Text(
                                    text = "${index + 1}",
                                    style = typography.bodyMedium.copy(
                                        fontFamily = FontFamily.Monospace,
                                        color = colors.onSurface.copy(alpha = 0.4f)
                                    ),
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.widthIn(min = 32.dp)
                                )
                            }
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            BasicTextField(
                                value = scriptContent,
                                onValueChange = onScriptChange,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(scrollState)
                                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                                    .focusRequester(focusRequester),
                                textStyle = typography.bodyMedium.copy(
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Transparent
                                ),
                                cursorBrush = SolidColor(colors.primary),
                                decorationBox = { innerTextField ->
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        Text(
                                            text = highlighter.highlight(scriptContent.text),
                                            style = typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)
                                        )

                                        Box {
                                            innerTextField()
                                        }
                                    }
                                }
                            )
                        }
                    }

                    VerticalScrollbar(
                        adapter = rememberScrollbarAdapter(scrollState),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .padding(end = 4.dp)
                    )
                }
            }
        }
    }
}