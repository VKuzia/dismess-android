package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import by.dismess.android.forms.MessageForm
import by.dismess.android.forms.MessageType
import by.dismess.android.ui.FlexChild
import androidx.compose.runtime.Composable as Composable

@Composable
fun DialogFrameImpl(chatName: String, messages: MutableList<String>, onBackToChats: () -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Column {
        TopPanel(chatName, onBackToChats)
        FlexChild(Modifier.weight(10f)) { MessageList(messages) }
        FlexChild(Modifier.weight(1f)) { TextPanel(textState, messages) }
    }
}

@Composable
fun TopPanel(chatName: String, onBackToChats: () -> Unit) {
    TopAppBar(
        title = { Text(chatName) },
        navigationIcon = {
            IconButton(onClick = onBackToChats) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Composable
fun MessageList(messages: MutableList<String>) {
    LazyColumn {
        items(messages) {
            MessageForm("Owner", it, MessageType.OWNERS)
            MessageForm("Other", it, MessageType.OTHERS)
        }
    }
}

@Composable
fun TextPanel(textState: MutableState<TextFieldValue>, messages: MutableList<String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .weight(6f)
                .fillMaxWidth()
        )
        Button(
            onClick = { onSendMessage(textState, messages) },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text("Send")
        }
    }
}

fun addMessage(message: String, messagesList: MutableList<String>) {
    messagesList.add(message)
}

fun onSendMessage(textState: MutableState<TextFieldValue>, messages: MutableList<String>) {
    addMessage(textState.value.text, messages)
    textState.value = TextFieldValue()
}
