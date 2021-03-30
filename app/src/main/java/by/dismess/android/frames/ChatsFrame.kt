package by.dismess.android.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.dismess.android.forms.ChatForm

val exampleOfChatsList = Array(30) { it.toString() }

@Composable
fun ChatsFrameImpl(onDialogStart: (String) -> Unit) {
    Column {
        Text("ChatList")
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            for (chatName in exampleOfChatsList) {
                ChatForm(chatName, onDialogStart)
            }
        }
    }
}