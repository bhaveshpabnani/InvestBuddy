package com.example.myapplication.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AppPrimary,
    secondary = AppSecondary,
    tertiary = AppTertiary,
    background = AppBackground,
    surface = AppSurface,
    surfaceVariant = AppSurfaceVariant,
    onPrimary = Color(0xFF06110D),
    onSecondary = Color(0xFF071018),
    onTertiary = Color(0xFF1A1300),
    onBackground = AppOnSurface,
    onSurface = AppOnSurface,
    onSurfaceVariant = AppOnSurfaceMuted
)

private val LightColorScheme = lightColorScheme(
    primary = AppPrimary,
    secondary = AppSecondary,
    tertiary = AppTertiary,
    background = Color(0xFFF4F8FC),
    surface = Color.White,
    surfaceVariant = Color(0xFFE9EEF5),
    onPrimary = Color(0xFF07110D),
    onSecondary = Color(0xFF071018),
    onTertiary = Color(0xFF1F1600),
    onBackground = Color(0xFF11151B),
    onSurface = Color(0xFF11151B),
    onSurfaceVariant = Color(0xFF546274)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
