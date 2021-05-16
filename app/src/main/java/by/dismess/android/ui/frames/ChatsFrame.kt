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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
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
import by.dismess.android.lib.get
import by.dismess.android.service.AppInfo
import by.dismess.android.service.DemoStorage
import by.dismess.android.service.model.Chat
import by.dismess.android.ui.controllers.ChatsFrameController
import by.dismess.android.ui.controllers.interfaces.ChatsFrameInterface
import by.dismess.android.ui.forms.ChatForm
import by.dismess.android.ui.forms.TextMapForm
import by.dismess.android.ui.helpers.BooleanToast
import by.dismess.android.ui.helpers.TopPanelIconButton
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import by.dismess.core.model.Invite
import by.dismess.core.network.retrievePublicSocketAddress
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.net.InetSocketAddress
import kotlin.reflect.KSuspendFunction0

@Composable
fun ChatsFrameImpl(
    onFindUser: () -> Unit,
    controller: ChatsFrameInterface = get(),
    onDialogStart: (Chat) -> Unit
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopPanel(
                { setShowDialog(true) },
                controller::refreshHistory,
                controller::retrieveInvite
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFindUser, backgroundColor = palette.primary) {
                Icon(Icons.Filled.Search, "")
            }
        },
        content = {
            LazyColumn {
                items(controller.getChatsList()) {
                    Divider(color = palette.primary, thickness = 1.dp)
                    ChatForm(it, onDialogStart)
                }
            }
        }
    )
    AboutDialog(
        controller.getAppVersion(),
        controller.getUserIdAsString(),
        showDialog,
        setShowDialog
    )
}

@Composable
private fun TopPanel(
    onAboutTriggered: () -> Unit,
    onRefreshHistory: () -> Unit,
    onInviteRetrieve: KSuspendFunction0<Invite?>
) {
    val historyRefreshRunningState = remember { mutableStateOf(false) }
    val refreshDoneState = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val toastShowState = remember { mutableStateOf(false) }
    val addressState = remember { mutableStateOf<InetSocketAddress?>(null) }
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
            TopPanelIconButton(
                imageVector = Icons.Filled.Share,
                onClick = {
                    GlobalScope.async {
//                        addressState.value = InetSocketAddress(InetAddress.getLocalHost().hostAddress, 1234)
                        addressState.value = retrievePublicSocketAddress(1234)!!
                        toastShowState.value = true
//                        Toast.makeText(context, "${address.address}:${address.port}", Toast.LENGTH_LONG).show()
//                        val invite = onInviteRetrieve()
//                        if (invite != null) {
//                            val shareIntent = Intent()
//                            shareIntent.action = Intent.ACTION_SEND
//                            shareIntent.type = "text/plain"
//                            shareIntent.putExtra(Intent.EXTRA_TEXT, invite.toInviteString())
//                            val chooserIntent = Intent.createChooser(shareIntent, "Share with")
//                            context.startActivity(chooserIntent)
//                        }
                    }
                }
            )
        }
    )
//    BooleanToast(refreshDoneState, "Refreshed")
    BooleanToast(toastShowState, "${addressState.value?.address}:${addressState.value?.port}")
}

@Composable
private fun AboutDialog(
    version: String,
    id: String,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit
) {
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
                    TextMapForm("Version", version)
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
    DismessTheme {
        Surface(color = palette.surface) {
            ChatsFrameImpl({}, ChatsFrameController(DemoStorage(), AppInfo("0.0.1"))) { }
        }
    }
}
