package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InviteFrameImpl(onValidInvite: () -> Unit) {
    Column {
        Text("InviteFrame")
        Button(onClick = onValidInvite) {
            Text("Proceed")
        }
    }
}

@Preview
@Composable
fun InviteFrameDefaultPreview() {
    InviteFrameImpl {}
}
