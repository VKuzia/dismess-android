package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InviteFrameImpl(validate: (String, String) -> Boolean, onValidInvite: () -> Unit) {
    val inviteFieldState = remember { mutableStateOf(TextFieldValue()) }
    val loginFieldState = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greet()
        Text("Enter service invite")
        InviteField {
            inviteFieldState.value = TextFieldValue(it)
        }

        Text("Enter your login")
        LoginField {
            loginFieldState.value = TextFieldValue(it)
        }
        Button(
            onClick = {
                if (validate(inviteFieldState.value.text, loginFieldState.value.text)) {
                    onValidInvite()
                }
            }
        ) {
            Text("Proceed")
        }
    }
}

@Composable
private fun Greet() {
    Text(
        "Welcome to Dismess Messenger",
        textAlign = TextAlign.Center
    )
}

@Composable
private fun InviteField(onInviteTextChanged: (String) -> Unit) {
    TextField(
        TextFieldValue(""),
        onValueChange = { onInviteTextChanged(it.text) }
    )
}

@Composable
private fun LoginField(onLoginTextChanged: (String) -> Unit) {
    TextField(
        TextFieldValue(""),
        onValueChange = { onLoginTextChanged(it.text) }
    )
}

@Preview
@Composable
private fun DefaultPreview() {
    Surface(color = MaterialTheme.colors.background) {
        InviteFrameImpl({ _: String, _: String -> true }) {}
    }
}
