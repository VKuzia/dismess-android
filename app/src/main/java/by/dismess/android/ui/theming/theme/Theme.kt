package by.dismess.android.ui.theming.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightThemePalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
)

private val DarkThemePalette = darkColors(
    primary = OrangePrimary,
    primaryVariant = BluePrimaryVariant,
    secondary = Purple200,
    background = BackgroundColor,
    surface = BackgroundColor,
)

@Composable
fun DismessTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkThemePalette
    } else {
        LightThemePalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
