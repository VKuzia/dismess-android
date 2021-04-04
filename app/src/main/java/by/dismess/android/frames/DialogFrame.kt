package by.dismess.android.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import by.dismess.android.forms.MessageForm
import by.dismess.android.forms.MessageType
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable as Composable

@Composable
fun DialogFrameImpl(chatName: String, messages: MutableList<String>, onBackToChats: () -> Unit) {
    val messagesList = remember { messages.toMutableStateList() }
    val lazyListState = remember { LazyListState() }
    val coroutineScope = rememberCoroutineScope()

    Column {
        TopPanel(chatName, onBackToChats)
        MessageList(Modifier.weight(10f), lazyListState, messagesList)
        TextPanel(Modifier.weight(1f)) {
            messagesList.add(it)
            coroutineScope.launch {
                lazyListState.animateScrollToItem(messagesList.lastIndex)
            }
        }
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
fun MessageList(modifier: Modifier, state: LazyListState, messages: SnapshotStateList<String>) {
    LazyColumn(modifier = modifier, state = state) {
        items(messages) {
            MessageForm(it, "3:45", MessageType.OWNERS)
            MessageForm(it, "3:45", MessageType.OTHERS)
        }
    }
}

@Composable
fun TextPanel(modifier: Modifier, onMessagesListUpdated: (String) -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Row(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            maxLines = 4,
            modifier = modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                onMessagesListUpdated(textState.value.text)
                textState.value = TextFieldValue()
            },
            modifier = Modifier.wrapContentWidth(Alignment.End)
        ) {
            Text("Send")
        }
    }
}
