package com.danilovfa.pexels.presentation.common.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.Black
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.Gray20
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.Gray40
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.Gray60
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.Gray80
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.Red
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.White

private val DarkColorScheme = darkColorScheme(
    primary = Red,
    secondary = Gray40,
    background = Black,
    primaryContainer = Red,
    onPrimaryContainer = White,
    secondaryContainer = Gray80,
    onSecondaryContainer = White,
    onBackground = White
)

private val LightColorScheme = lightColorScheme(
    primary = Red,
    secondary = Gray60,
    background = White,
    primaryContainer = Red,
    onPrimaryContainer = White,
    secondaryContainer = Gray20,
    onSecondaryContainer = Black,
    onBackground = Black
)

@Composable
fun PexelsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
