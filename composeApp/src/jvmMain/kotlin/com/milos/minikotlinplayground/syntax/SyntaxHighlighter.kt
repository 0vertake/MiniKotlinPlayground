package com.milos.minikotlinplayground.syntax

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

class SyntaxHighlighter {
    private val keywords = setOf(
        "fun", "val", "var", "class", "interface",
        "if", "else", "when", "for", "while",
        "return", "import", "package", "object", "companion"
    )

    private val keywordColor = Color(0xFFCC7832)
    private val defaultColor = Color(0xFF6A6A6A)

    fun highlight(code: String): AnnotatedString = buildAnnotatedString {
        val regex = Regex("""(\b\w+\b|\s+|[^\w\s])""")

        regex.findAll(code).forEach { match ->
            val text = match.value
            val color = if (keywords.contains(text)) keywordColor else defaultColor

            withStyle(SpanStyle(color = color)) {
                append(text)
            }
        }
    }
}
