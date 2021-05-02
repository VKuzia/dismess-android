package by.dismess.android.ui.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.dismess.android.R
import by.dismess.android.ui.helpers.CircularImage
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette

@Composable
fun ChatForm(chatName: String, onClick: (String) -> Unit, lastMessage: String = "...") {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(chatName) })
            .background(color = palette.background)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularImage(R.drawable.default_user_preview)
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    chatName, modifier = Modifier.padding(6.dp),
                    style = MaterialTheme.typography.h5
                )
                Text(
                    lastMessage, modifier = Modifier.padding(6.dp),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatFormDefaultPreview() {
    DismessTheme {
        Surface(color = palette.surface) {
            ChatForm("Some chat", {}, "Last message")
        }
    }
}
