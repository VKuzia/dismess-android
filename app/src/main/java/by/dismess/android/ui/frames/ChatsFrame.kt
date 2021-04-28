package by.dismess.android.ui.frames

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import by.dismess.android.ui.forms.ChatForm
import by.dismess.android.ui.forms.TextMapForm

@Composable
fun ChatsFrameImpl(
    chatList: Array<String>,
    onAddChat: () -> Unit,
    onDialogStart: (String) -> Unit
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    Column {
        TopPanel({ setShowDialog(true) }, onAddChat)
        LazyColumn {
            items(chatList) {
                ChatForm(it, onDialogStart)
            }
        }
    }
    AboutDialog("12345678", showDialog, setShowDialog)
}

@Composable
private fun TopPanel(onAboutTriggered: () -> Unit, onAddChat: () -> Unit) {
    TopAppBar(
        title = { Text("Dismess") },
        navigationIcon = {
            IconButton(onClick = onAboutTriggered) {
                Icon(Icons.Filled.Info, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = onAddChat) {
                Icon(Icons.Filled.AddCircle, contentDescription = null)
            }
        }
    )
}

@Composable
private fun AboutDialog(id: String, showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    val (copiedState, setCopiedState) = remember { mutableStateOf(false) }
    if (showDialog) {
        Dialog(onDismissRequest = { setShowDialog(false) }) {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
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
                            onClick = {
                                setCopiedState(true)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Copy ID")
                        }
                    }
                }
            }
        }
    }
    CopyToClipboard(id, copiedState, setCopiedState)
}

@Composable
private fun CopyToClipboard(text: String, copiedState: Boolean, setCopiedState: (Boolean) -> Unit) {
    if (copiedState) {
        val clipboardManager =
            LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(LocalContext.current, "ID copied to clipboard!", Toast.LENGTH_LONG).show()
        setCopiedState(false)
    }
}

@Preview
@Composable
private fun ChatsFrameDefaultPreview() {
    val chatList = arrayOf("One", "Two", "Three", "Four")
    ChatsFrameImpl(chatList, {}) { }
}

@Preview
@Composable
private fun ChatsFrameAboutDialogPreview() {
    AboutDialog(id = "12345", showDialog = true) {}
}
