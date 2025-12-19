package com.milos.minikotlinplayground.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import minikotlinplayground.composeapp.generated.resources.Res
import minikotlinplayground.composeapp.generated.resources.kotlin_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun EditorPane(
    scriptContent: String,
    isRunning: Boolean,
    onScriptChange: (String) -> Unit,
    onRunClick: () -> Unit,
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
                TextField(
                    value = scriptContent,
                    onValueChange = onScriptChange,
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
}
