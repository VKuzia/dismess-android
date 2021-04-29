package by.dismess.android.ui.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.dismess.android.ui.theming.theme.BackgroundColor
import by.dismess.android.ui.theming.theme.BackgroundColorDarker
import by.dismess.android.ui.theming.theme.DismessTheme

@Composable
fun ChatForm(chatName: String, onClick: (String) -> Unit, lastMessage: String = "...") {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(chatName) })
            .background(color = BackgroundColorDarker)
    ) {
        Column {
            Text(chatName, modifier = Modifier.padding(6.dp), fontSize = 24.sp)
            Text(lastMessage, modifier = Modifier.padding(6.dp))
        }
    }
}

@Preview
@Composable
private fun ChatFormDefaultPreview() {
    DismessTheme(true) {
        Surface(color = BackgroundColor) {
            ChatForm("Some chat", {}, "Last message")
        }
    }
}
