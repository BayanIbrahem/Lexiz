package com.dev.bayan.ibrahim.feature.quiz_builder.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


val QuizBuilderLightColorScheme = lightColorScheme(
    primary = quiz_builder_light_primary,
    onPrimary = quiz_builder_light_onPrimary,
    primaryContainer = quiz_builder_light_primaryContainer,
    onPrimaryContainer = quiz_builder_light_onPrimaryContainer,
    secondary = quiz_builder_light_secondary,
    onSecondary = quiz_builder_light_onSecondary,
    secondaryContainer = quiz_builder_light_secondaryContainer,
    onSecondaryContainer = quiz_builder_light_onSecondaryContainer,
    tertiary = quiz_builder_light_tertiary,
    onTertiary = quiz_builder_light_onTertiary,
    tertiaryContainer = quiz_builder_light_tertiaryContainer,
    onTertiaryContainer = quiz_builder_light_onTertiaryContainer,
    error = quiz_builder_light_error,
    onError = quiz_builder_light_onError,
    errorContainer = quiz_builder_light_errorContainer,
    onErrorContainer = quiz_builder_light_onErrorContainer,
    outline = quiz_builder_light_outline,
    background = quiz_builder_light_background,
    onBackground = quiz_builder_light_onBackground,
    surface = quiz_builder_light_surface,
    onSurface = quiz_builder_light_onSurface,
    surfaceVariant = quiz_builder_light_surfaceVariant,
    onSurfaceVariant = quiz_builder_light_onSurfaceVariant,
    inverseSurface = quiz_builder_light_inverseSurface,
    inverseOnSurface = quiz_builder_light_inverseOnSurface,
    inversePrimary = quiz_builder_light_inversePrimary,
    surfaceTint = quiz_builder_light_surfaceTint,
    outlineVariant = quiz_builder_light_outlineVariant,
    scrim = quiz_builder_light_scrim,
)


val QuizBuilderDarkColorScheme = darkColorScheme(
    primary = quiz_builder_dark_primary,
    onPrimary = quiz_builder_dark_onPrimary,
    primaryContainer = quiz_builder_dark_primaryContainer,
    onPrimaryContainer = quiz_builder_dark_onPrimaryContainer,
    secondary = quiz_builder_dark_secondary,
    onSecondary = quiz_builder_dark_onSecondary,
    secondaryContainer = quiz_builder_dark_secondaryContainer,
    onSecondaryContainer = quiz_builder_dark_onSecondaryContainer,
    tertiary = quiz_builder_dark_tertiary,
    onTertiary = quiz_builder_dark_onTertiary,
    tertiaryContainer = quiz_builder_dark_tertiaryContainer,
    onTertiaryContainer = quiz_builder_dark_onTertiaryContainer,
    error = quiz_builder_dark_error,
    onError = quiz_builder_dark_onError,
    errorContainer = quiz_builder_dark_errorContainer,
    onErrorContainer = quiz_builder_dark_onErrorContainer,
    outline = quiz_builder_dark_outline,
    background = quiz_builder_dark_background,
    onBackground = quiz_builder_dark_onBackground,
    surface = quiz_builder_dark_surface,
    onSurface = quiz_builder_dark_onSurface,
    surfaceVariant = quiz_builder_dark_surfaceVariant,
    onSurfaceVariant = quiz_builder_dark_onSurfaceVariant,
    inverseSurface = quiz_builder_dark_inverseSurface,
    inverseOnSurface = quiz_builder_dark_inverseOnSurface,
    inversePrimary = quiz_builder_dark_inversePrimary,
    surfaceTint = quiz_builder_dark_surfaceTint,
    outlineVariant = quiz_builder_dark_outlineVariant,
    scrim = quiz_builder_dark_scrim,
)

@Composable
internal fun JaArQuizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> QuizBuilderDarkColorScheme
        else -> QuizBuilderLightColorScheme
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

