package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DialogFrameImpl(onBackToChats: () -> Unit, chatName: String) {
    Column {
        Text(chatName)
        Button(onBackToChats) {
            Text("Back To Chats")
        }
    }
}
