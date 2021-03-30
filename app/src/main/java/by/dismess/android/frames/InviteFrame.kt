package by.dismess.android.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun InviteFrameImpl(onValidInvite: () -> Unit) {
    Column {
        Text("InviteFrame")
        Button(onClick = onValidInvite) {
            Text("Proceed")
        }
    }

}

