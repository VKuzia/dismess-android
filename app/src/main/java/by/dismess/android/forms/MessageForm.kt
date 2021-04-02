package by.dismess.android.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.dismess.android.ui.theme.OTHERS_MESSAGE_COLOR
import by.dismess.android.ui.theme.OWNERS_MESSAGE_COLOR

enum class MessageType {
    OWNERS, OTHERS, PROGRAMS,
}

@Composable
fun MessageForm(author: String, message: String, messageType: MessageType) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .background(getColorForType(messageType))
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = getAlignmentForType(messageType)) {
            Text(author, fontSize = 24.sp)
            Text(message)
        }
    }
}

fun getAlignmentForType(type: MessageType): Alignment.Horizontal {
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

fun getColorForType(type: MessageType): Color {
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
fun MessageFormDefaultPreview() {
    Column {
        MessageForm(author = "Author", message = "Message", messageType = MessageType.OWNERS)
        MessageForm(author = "Aor", message = "Mee", messageType = MessageType.OTHERS)
    }
}
