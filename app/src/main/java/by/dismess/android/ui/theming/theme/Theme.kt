package by.dismess.android.ui.theming.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

val palette = darkColors(
    primary = OrangePrimary,
    primaryVariant = BluePrimaryVariant,
    secondary = Purple200,
    background = BackgroundColor,
    surface = BackgroundColor
)

@Composable
fun DismessTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = palette,
        shapes = Shapes,
        content = content
    )
}
