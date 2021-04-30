package by.dismess.android.ui.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import by.dismess.android.ui.theming.theme.palette

@Composable
fun CircularImage(drawableId: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = "",
        modifier = modifier.clip(CircleShape),
    )
}

@Composable
fun TopPanelIconButton(onClick: () -> Unit, imageVector: ImageVector) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = palette.primary,
            modifier = Modifier.fillMaxSize(0.9f)
        )
    }
}
