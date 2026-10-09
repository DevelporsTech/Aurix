package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AurixColorScheme = darkColorScheme(
    primary = AurixCyanElectric,
    onPrimary = Color(0xFF001F28),
    primaryContainer = Color(0xFF004D5A),
    onPrimaryContainer = Color(0xFF88F2FF),
    secondary = AurixAzureNeon,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF14305A),
    onSecondaryContainer = Color(0xFFB8D3FF),
    tertiary = AurixGoldAccent,
    onTertiary = Color(0xFF332000),
    background = AurixObsidianBlack,
    onBackground = AurixTextPrimary,
    surface = AurixGraphiteDark,
    onSurface = AurixTextPrimary,
    surfaceVariant = AurixSurfaceCard,
    onSurfaceVariant = AurixTextSecondary,
    outline = AurixBorderSubtle,
    outlineVariant = AurixBorderGlass
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AurixColorScheme,
        typography = Typography,
        content = content
    )
}
