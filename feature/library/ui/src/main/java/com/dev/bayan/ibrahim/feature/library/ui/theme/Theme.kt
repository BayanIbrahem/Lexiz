package com.dev.bayan.ibrahim.feature.library.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LibraryLightColorScheme = lightColorScheme(
    primary = library_light_primary,
    onPrimary = library_light_onPrimary,
    primaryContainer = library_light_primaryContainer,
    onPrimaryContainer = library_light_onPrimaryContainer,
    secondary = library_light_secondary,
    onSecondary = library_light_onSecondary,
    secondaryContainer = library_light_secondaryContainer,
    onSecondaryContainer = library_light_onSecondaryContainer,
    tertiary = library_light_tertiary,
    onTertiary = library_light_onTertiary,
    tertiaryContainer = library_light_tertiaryContainer,
    onTertiaryContainer = library_light_onTertiaryContainer,
    error = library_light_error,
    errorContainer = library_light_errorContainer,
    onError = library_light_onError,
    onErrorContainer = library_light_onErrorContainer,
    background = library_light_background,
    onBackground = library_light_onBackground,
    outline = library_light_outline,
    inverseOnSurface = library_light_inverseOnSurface,
    inverseSurface = library_light_inverseSurface,
    inversePrimary = library_light_inversePrimary,
    surfaceTint = library_light_surfaceTint,
    outlineVariant = library_light_outlineVariant,
    scrim = library_light_scrim,
    surface = library_light_surface,
    onSurface = library_light_onSurface,
    surfaceVariant = library_light_surfaceVariant,
    onSurfaceVariant = library_light_onSurfaceVariant,
)


private val LibraryDarkColorScheme = darkColorScheme(
    primary = library_dark_primary,
    onPrimary = library_dark_onPrimary,
    primaryContainer = library_dark_primaryContainer,
    onPrimaryContainer = library_dark_onPrimaryContainer,
    secondary = library_dark_secondary,
    onSecondary = library_dark_onSecondary,
    secondaryContainer = library_dark_secondaryContainer,
    onSecondaryContainer = library_dark_onSecondaryContainer,
    tertiary = library_dark_tertiary,
    onTertiary = library_dark_onTertiary,
    tertiaryContainer = library_dark_tertiaryContainer,
    onTertiaryContainer = library_dark_onTertiaryContainer,
    error = library_dark_error,
    errorContainer = library_dark_errorContainer,
    onError = library_dark_onError,
    onErrorContainer = library_dark_onErrorContainer,
    background = library_dark_background,
    onBackground = library_dark_onBackground,
    outline = library_dark_outline,
    inverseOnSurface = library_dark_inverseOnSurface,
    inverseSurface = library_dark_inverseSurface,
    inversePrimary = library_dark_inversePrimary,
    surfaceTint = library_dark_surfaceTint,
    outlineVariant = library_dark_outlineVariant,
    scrim = library_dark_scrim,
    surface = library_dark_surface,
    onSurface = library_dark_onSurface,
    surfaceVariant = library_dark_surfaceVariant,
    onSurfaceVariant = library_dark_onSurfaceVariant,
)

@Composable
fun JaArLibraryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> LibraryDarkColorScheme
        else -> LibraryLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val ColorScheme.primarySurfaceContainer: Color
    get() {
        return lerp(
            start = primaryContainer,
            stop = surface,
            fraction = 0.5f
        )
    }

val ColorScheme.secondarySurfaceContainer: Color
    get() {
        return lerp(
            start = secondaryContainer,
            stop = surface,
            fraction = 0.5f
        )
    }
