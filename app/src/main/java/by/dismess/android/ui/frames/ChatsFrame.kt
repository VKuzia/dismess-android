package by.dismess.android.ui.frames

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import by.dismess.android.ui.forms.ChatForm
import by.dismess.android.ui.forms.TextMapForm
import by.dismess.android.ui.helpers.BooleanToast
import by.dismess.android.ui.helpers.TopPanelIconButton
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatsFrameImpl(
    chatList: Array<String>,
    onRefreshHistory: () -> Unit,
    onFindUser: () -> Unit,
    onDialogStart: (String) -> Unit
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    Column {
        TopPanel({ setShowDialog(true) }, onRefreshHistory, onFindUser)
        LazyColumn {
            items(chatList) {
                Divider(color = palette.primary, thickness = 1.dp)
                ChatForm(it, onDialogStart)
            }
        }
    }
    AboutDialog("12345678", showDialog, setShowDialog)
}

@Composable
private fun TopPanel(
    onAboutTriggered: () -> Unit,
    onRefreshHistory: () -> Unit,
    onFindUser: () -> Unit
) {
    val historyRefreshRunningState = remember { mutableStateOf(false) }
    val refreshDoneState = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(
                "Dismess",
                style = MaterialTheme.typography.h5
            )
        },
        navigationIcon = {
            TopPanelIconButton(onClick = onAboutTriggered, Icons.Filled.Info)
        },
        actions = {
            if (!historyRefreshRunningState.value) {
                TopPanelIconButton(
                    onClick = {
                        // Demo. We emulate history refreshing
                        coroutineScope.launch {
                            historyRefreshRunningState.value = true
                            onRefreshHistory()
                            delay(3000)
                            refreshDoneState.value = true
                            historyRefreshRunningState.value = false
                        }
                    },
                    Icons.Filled.Refresh
                )
            } else {
                CircularProgressIndicator()
            }
            TopPanelIconButton(onClick = onFindUser, imageVector = Icons.Filled.Search)
        }
    )
    BooleanToast(refreshDoneState, "Refreshed")
}

@Composable
private fun AboutDialog(id: String, showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    val copiedState = remember { mutableStateOf(false) }
    if (showDialog) {
        Dialog(onDismissRequest = { setShowDialog(false) }) {
            Surface(
                color = palette.surface,
                shape = RoundedCornerShape(5)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("About Dismess")
                    TextMapForm("Version", "0.0.1")
                    TextMapForm("Company", "Dismess")
                    Divider(modifier = Modifier.padding(10.dp))
                    TextMapForm("Your ID", id)
                    Row(modifier = Modifier.padding(6.dp)) {
                        Button(
                            onClick = { setShowDialog(false) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Back")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = { copiedState.value = true },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Copy ID")
                        }
                    }
                }
            }
        }
    }
    CopyToClipboard(id, copiedState)
}

@Composable
private fun CopyToClipboard(text: String, copiedState: MutableState<Boolean>) {
    if (copiedState.value) {
        val clipboardManager =
            LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clip)
        BooleanToast(toastShowState = copiedState, text = "ID copied to clipboard!")
    }
}

@Preview
@Composable
private fun ChatsFrameDefaultPreview() {
    val chatList = arrayOf("One", "Two", "Three", "Four", "Five")
    DismessTheme {
        Surface(color = palette.surface) {
            ChatsFrameImpl(chatList, {}, {}) { }
        }
    }
}

@Preview
@Composable
private fun ChatsFrameAboutDialogPreview() {
    DismessTheme {
        Surface(color = palette.surface) {
            AboutDialog(id = "12345", showDialog = true) {}
        }
    }
}
