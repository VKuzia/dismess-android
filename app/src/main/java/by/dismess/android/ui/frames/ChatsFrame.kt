package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import by.dismess.android.ui.forms.ChatForm

@Composable
fun ChatsFrameImpl(chatList: Array<String>, onDialogStart: (String) -> Unit) {
    Column {
        Text("ChatsFrame", fontSize = 30.sp)
        LazyColumn {
            items(chatList) {
                ChatForm(it, onDialogStart)
            }
        }
    }
}
