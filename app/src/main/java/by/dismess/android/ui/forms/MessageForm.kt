package by.dismess.android.ui.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.othersMessageColor
import by.dismess.android.ui.theming.theme.ownersMessageColor
import by.dismess.android.ui.theming.theme.palette

enum class MessageType {
    OWNERS, OTHERS,
}

@Composable
fun MessageForm(message: String, date: String, messageType: MessageType) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .wrapContentWidth(getAlignmentForType(messageType))
    ) {
        Row(modifier = Modifier.background(getColorForType(messageType))) {
            Text(
                message,
                modifier = Modifier
                    .widthIn(0.dp, 200.dp)
                    .padding(4.dp)
            )
            Text(
                date,
                fontSize = 9.sp,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(4.dp)
            )
        }
    }
}

private fun getAlignmentForType(type: MessageType): Alignment.Horizontal {
    return when (type) {
        MessageType.OWNERS -> {
            Alignment.End
        }
        MessageType.OTHERS -> {
            Alignment.Start
        }
    }
}

private fun getColorForType(type: MessageType): Color {
    return when (type) {
        MessageType.OWNERS -> {
            ownersMessageColor
        }
        MessageType.OTHERS -> {
            othersMessageColor
        }
    }
}

@Preview
@Composable
private fun MessageFormDefaultPreview() {
    DismessTheme {
        Surface(color = palette.surface) {
            Column {
                MessageForm(message = "Message", date = "20:44", messageType = MessageType.OWNERS)
                MessageForm(
                    message = "Long ".repeat(10) + "Message",
                    date = "20:45",
                    messageType = MessageType.OWNERS
                )
                MessageForm(
                    message = "Long ".repeat(10) + "Message",
                    date = "12:33",
                    messageType = MessageType.OTHERS
                )
                MessageForm(message = "Short", date = "13:22", messageType = MessageType.OTHERS)
                MessageForm(
                    message = "Long ".repeat(18) + "Message",
                    date = "20:45",
                    messageType = MessageType.OWNERS
                )
            }
        }
    }
}
