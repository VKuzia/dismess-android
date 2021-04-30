package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import by.dismess.android.R
import by.dismess.android.ui.forms.MessageForm
import by.dismess.android.ui.forms.MessageType
import by.dismess.android.ui.helpers.BooleanToast
import by.dismess.android.ui.helpers.CircularImage
import by.dismess.android.ui.helpers.TopPanelIconButton
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable as Composable

@Composable
fun DialogFrameImpl(chatName: String, messages: MutableList<String>, onBackToChats: () -> Unit) {
    val messagesList = remember { messages.toMutableStateList() }
    val historyRefreshRunningState = remember { mutableStateOf(false) }
    val lazyListState = remember { LazyListState() }
    val coroutineScope = rememberCoroutineScope()

    Column {
        TopPanel(chatName, historyRefreshRunningState, { }, onBackToChats)
        MessageList(Modifier.weight(10f), lazyListState, messagesList)
        SearchPanel(Modifier.weight(1f)) {
            messagesList.add(it)
            coroutineScope.launch {
                lazyListState.animateScrollToItem(messagesList.lastIndex)
            }
        }
    }
}

@Composable
private fun TopPanel(
    chatName: String,
    historyRefreshRunningState: MutableState<Boolean>,
    onRefreshHistory: () -> Unit,
    onBackToChats: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val refreshDoneState = remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            CircularImage(R.drawable.default_user_preview, modifier = Modifier.fillMaxHeight(0.8f))
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            Text(chatName)
        },
        navigationIcon = {
            TopPanelIconButton(onClick = onBackToChats, imageVector = Icons.Filled.ArrowBack)
        },
        actions = {
            if (historyRefreshRunningState.value) {
                CircularProgressIndicator()
                return@TopAppBar
            }
            TopPanelIconButton(
                onClick = {
                    coroutineScope.launch {
                        // Demo. We emulate history refreshing
                        historyRefreshRunningState.value = true
                        onRefreshHistory()
                        delay(3000)
                        refreshDoneState.value = true
                        historyRefreshRunningState.value = false
                    }
                },
                Icons.Filled.Refresh
            )
        }
    )
    BooleanToast(refreshDoneState, "Refreshed")
}

@Composable
private fun MessageList(
    modifier: Modifier,
    state: LazyListState,
    messages: SnapshotStateList<String>
) {
    LazyColumn(modifier = modifier, state = state) {
        items(messages) {
            MessageForm(it, "3:45", MessageType.OWNERS)
            MessageForm(it, "3:45", MessageType.OTHERS)
        }
    }
}

@Composable
private fun SearchPanel(modifier: Modifier, onMessagesListUpdated: (String) -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Row(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            maxLines = 4,
            modifier = modifier.fillMaxSize(),
            placeholder = { Text("Message") }
        )
        Button(
            onClick = {
                onMessagesListUpdated(textState.value.text)
                textState.value = TextFieldValue()
            },
            modifier = Modifier
                .wrapContentWidth(Alignment.End)
                .fillMaxHeight()
        ) {
            Text("Send")
        }
    }
}

@Preview
@Composable
private fun DialogFrameDefaultPreview() {
    val messagesList = mutableListOf("Hello", "Hi", "Goodbye", "Chao")
    DismessTheme {
        Surface(color = palette.surface) {
            DialogFrameImpl(chatName = "ChatName", messages = messagesList) { }
        }
    }
}

@Preview
@Composable
private fun DialogTopPanelDefaultPreview() {
    DismessTheme {
        Surface(color = palette.surface) {
            TopPanel(chatName = "chatName", mutableStateOf(false), {}) { }
        }
    }
}
