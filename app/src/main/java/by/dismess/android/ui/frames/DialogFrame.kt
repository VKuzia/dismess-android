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
import androidx.compose.runtime.Composable
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
import by.dismess.android.lib.get
import by.dismess.android.service.DemoStorage
import by.dismess.android.service.model.Chat
import by.dismess.android.ui.controllers.DialogFrameController
import by.dismess.android.ui.controllers.interfaces.DialogFrameInterface
import by.dismess.android.ui.forms.MessageForm
import by.dismess.android.ui.forms.MessageType
import by.dismess.android.ui.helpers.BooleanToast
import by.dismess.android.ui.helpers.CircularImage
import by.dismess.android.ui.helpers.TopPanelIconButton
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import by.dismess.core.chating.elements.Message
import by.dismess.core.utils.UniqID
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DialogFrameImpl(controller: DialogFrameInterface = get(), onBackToChats: () -> Unit) {
    val messagesList = controller.getMessages().toMutableStateList()
    val historyRefreshRunningState = remember { mutableStateOf(false) }
    val lazyListState = remember { LazyListState() }
    val coroutineScope = rememberCoroutineScope()
    controller.registerMessagesListener { message -> messagesList.add(message) }
    Column {
        TopPanel(
            historyRefreshRunningState,
            controller.getChatName(),
            controller::refreshHistory,
            onBackToChats
        )
        MessageList(Modifier.weight(10f), lazyListState, messagesList, controller.getOwnId())
        TextPanel(Modifier.weight(1f)) {
            controller.sendMessage(it)
            coroutineScope.launch {
                lazyListState.animateScrollToItem(messagesList.lastIndex)
            }
        }
    }
}

@Composable
private fun MessageList(
    modifier: Modifier,
    state: LazyListState,
    messages: SnapshotStateList<Message>,
    ownId: UniqID
) {
    val dateFormat = SimpleDateFormat("hh:mm", Locale.ENGLISH)
    LazyColumn(modifier = modifier, state = state) {
        items(messages) {
            MessageForm(
                it.text,
                dateFormat.format(it.date),
                if (it.senderID == ownId) MessageType.OWNERS else MessageType.OTHERS
            )
        }
    }
}

@Composable
private fun TextPanel(modifier: Modifier, sendMessage: (String) -> Unit) {
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
                sendMessage(textState.value.text)
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

@Composable
private fun TopPanel(
    historyRefreshRunningState: MutableState<Boolean>,
    chatName: String,
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

@Preview
@Composable
private fun DialogFrameDefaultPreview() {
    DismessTheme {
        Surface(color = palette.surface) {
            DialogFrameImpl(
                DialogFrameController(
                    DemoStorage(),
                    Chat("1", UniqID("0"))
                )
            ) { }
        }
    }
}
