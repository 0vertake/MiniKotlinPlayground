package com.milos.minikotlinplayground.util

import androidx.compose.ui.text.input.TextFieldValue

fun lineColumnToPosition(textFieldValue: TextFieldValue, line: Int, column: Int): Int {
    val text = textFieldValue.text
    val lines = text.split("\n")
    var position = 0

    for (i in 0 until minOf(line - 1, lines.size)) {
        position += lines[i].length + 1 // +1 for newline character
    }

    if (line - 1 < lines.size) {
        position += minOf(column - 1, lines[line - 1].length)
    }

    return position
}
