package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import by.dismess.android.forms.MessageForm
import by.dismess.android.forms.MessageType
import androidx.compose.runtime.Composable as Composable

@Composable
fun DialogFrameImpl(chatName: String, messages: MutableList<String>, onBackToChats: () -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Column {
        Text(chatName, modifier = Modifier.weight(1f))
        Button(
            onBackToChats,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text("Back To Chats")
        }
        Box(
            modifier = Modifier
                .weight(10f)
                .fillMaxWidth()
        ) {
            MessageList(messages = messages)
        }
        Button(
            {
                AddMessage(textState.value.text, messages)
                textState.value = TextFieldValue()
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text("Add message")
        }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun MessageList(messages: MutableList<String>) {
    LazyColumn {
        items(messages) {
            MessageForm(author = "Owner", message = it, messageType = MessageType.OWNERS)
            MessageForm(author = "Other", message = it, messageType = MessageType.OTHERS)
        }
    }
}

fun AddMessage(message: String, messagesList: MutableList<String>) {
    messagesList.add(message)
}
