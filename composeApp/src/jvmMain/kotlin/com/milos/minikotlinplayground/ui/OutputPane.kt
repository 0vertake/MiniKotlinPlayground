package com.milos.minikotlinplayground.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun OutputPane(
    output: String,
    onErrorLocationClick: (line: Int, column: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val lazyListState = rememberLazyListState()
    val lines = remember(output) { output.lines() }

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
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        items(lines) { line ->
                            val annotatedLine = parseAndAnnotateErrors(line, colors.primary, onErrorLocationClick)
                            BasicText(
                                text = annotatedLine,
                                style = typography.bodyMedium.copy(
                                    fontFamily = FontFamily.Monospace,
                                    color = colors.onSurface
                                )
                            )
                        }
                    }

                    VerticalScrollbar(
                        adapter = rememberScrollbarAdapter(lazyListState),
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

private fun parseAndAnnotateErrors(
    output: String,
    linkColor: Color,
    onErrorLocationClick: (line: Int, column: Int) -> Unit
): AnnotatedString {
    val errorLocationRegex = Regex("""(\w+\.?\w*):(\d+):(\d+):""")

    return buildAnnotatedString {
        var lastIndex = 0

        errorLocationRegex.findAll(output).forEach { match ->
            append(output.substring(lastIndex, match.range.first))

            val locationText = match.value
            val line = match.groupValues[2].toIntOrNull() ?: 0
            val column = match.groupValues[3].toIntOrNull() ?: 0

            withLink(
                LinkAnnotation.Clickable(
                    tag = "ERROR_LOCATION",
                    linkInteractionListener = {
                        onErrorLocationClick(line, column)
                    }
                )
            ) {
                withStyle(
                    SpanStyle(
                        color = linkColor,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(locationText)
                }
            }

            lastIndex = match.range.last + 1
        }

        if (lastIndex < output.length) {
            append(output.substring(lastIndex))
        }
    }
}