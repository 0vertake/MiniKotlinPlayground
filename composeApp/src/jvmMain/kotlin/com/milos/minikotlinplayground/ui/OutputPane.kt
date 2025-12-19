package com.milos.minikotlinplayground.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
                val annotatedOutput = parseAndAnnotateErrors(output, colors.primary, onErrorLocationClick)

                BasicText(
                    text = annotatedOutput,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    style = typography.bodyMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        color = colors.onSurface
                    )
                )
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
