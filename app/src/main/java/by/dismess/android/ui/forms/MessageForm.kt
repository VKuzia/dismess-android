package by.dismess.android.ui.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.dismess.android.ui.theming.theme.OTHERS_MESSAGE_COLOR
import by.dismess.android.ui.theming.theme.OWNERS_MESSAGE_COLOR

enum class MessageType {
    OWNERS, OTHERS, PROGRAMS,
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
        Row(
            modifier = Modifier
                .background(getColorForType(messageType))
        ) {
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
                    .padding(4.dp),
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
        else -> {
            Alignment.CenterHorizontally
        }
    }
}

private fun getColorForType(type: MessageType): Color {
    return when (type) {
        MessageType.OWNERS -> {
            OWNERS_MESSAGE_COLOR
        }
        MessageType.OTHERS -> {
            OTHERS_MESSAGE_COLOR
        }
        else -> Color.White
    }
}

@Preview
@Composable
private fun MessageFormDefaultPreview() {
    Column {
        MessageForm(message = "Message", date = "20:44", messageType = MessageType.OWNERS)
        MessageForm(
            message = "Long Long Long Long Long Long Long Long Long Message",
            date = "20:45",
            messageType = MessageType.OWNERS
        )
        MessageForm(
            message = "Long Long Long Long Long Long Long Long Long Message",
            date = "12:33",
            messageType = MessageType.OTHERS
        )
        MessageForm(message = "Mee", date = "13:22", messageType = MessageType.OTHERS)
        MessageForm(
            message = "Long Long Long Long Long Long Long Long Long Long " +
                "Long Long Long Long Long Long Long Long Message",
            date = "20:45",
            messageType = MessageType.OWNERS
        )
        MessageForm(message = "Short", date = "13:24", messageType = MessageType.OTHERS)
    }
}
