package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import by.dismess.android.ui.theming.theme.BackgroundColor
import by.dismess.android.ui.theming.theme.DismessTheme

@Composable
fun InviteFrameImpl(validate: (String, String) -> Boolean, onValidInvite: () -> Unit) {
    val inviteFieldState = remember { mutableStateOf(TextFieldValue()) }
    val loginFieldState = remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greet()
        Text("Enter service invite")
        InviteField(inviteFieldState)
        Text("Enter your login")
        LoginField(loginFieldState)
        Button(
            onClick = {
                if (validate(inviteFieldState.value.text, loginFieldState.value.text)) {
                    onValidInvite()
                }
            }
        ) {
            Text("Proceed")
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.4f))
    }
}

@Composable
private fun Greet() {
    Text(
        "Welcome to Dismess",
        textAlign = TextAlign.Center
    )
}

@Composable
private fun InviteField(fieldState: MutableState<TextFieldValue>) {
    TextField(
        value = fieldState.value,
        onValueChange = { fieldState.value = it }
    )
}

@Composable
private fun LoginField(fieldState: MutableState<TextFieldValue>) {
    TextField(
        value = fieldState.value,
        onValueChange = { fieldState.value = it }
    )
}

@Preview
@Composable
private fun DefaultPreview() {
    DismessTheme(true) {
        Surface(color = BackgroundColor) {
            InviteFrameImpl({ _: String, _: String -> true }) {}
        }
    }
}
