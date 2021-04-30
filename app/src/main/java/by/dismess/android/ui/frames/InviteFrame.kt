package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import by.dismess.android.ui.helpers.CustomTextField
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

private const val someValidationError = "Validation Error :(\nTry again please" // Demo only

@Composable
fun InviteFrameImpl(validate: (String, String) -> Boolean, onValidInvite: () -> Unit) {
    val inviteFieldState = remember { mutableStateOf(TextFieldValue()) }
    val loginFieldState = remember { mutableStateOf(TextFieldValue()) }
    val runningValidationState = remember { mutableStateOf(false) }
    val errorMessageState = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greet()
        Box(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .wrapContentSize()
        ) {
            StatusPanel(runningValidationState, errorMessageState)
        }
        CustomTextField(loginFieldState, "Enter your login")
        CustomTextField(inviteFieldState, "Enter service invite")
        Button(
            onClick = {
                coroutineScope.launch {
                    runningValidationState.value = true
                    var validationResult =
                        validate(inviteFieldState.value.text, loginFieldState.value.text)
                    // These lines are for demo only. We emulate hard work of validation.
                    delay(2000)
                    if (Random.nextBoolean()) {
                        validationResult = false
                    }
                    runningValidationState.value = false
                    if (validationResult) {
                        errorMessageState.value = null
                        onValidInvite()
                    } else {
                        errorMessageState.value = someValidationError
                    }
                }
            }
        ) {
            Text("Proceed")
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.2f))
    }
}

@Composable
fun StatusPanel(
    runningValidationState: MutableState<Boolean>,
    errorMessageState: MutableState<String?>
) {
    if (runningValidationState.value) {
        CircularProgressIndicator()
    } else {
        if (errorMessageState.value != null) {
            Text(
                text = someValidationError,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Composable
private fun Greet() {
    Text(
        "Welcome to Dismess",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h2
    )
    Text(
        "Best distributed messenger of all time",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.subtitle1,
        color = palette.primary,
        textDecoration = TextDecoration.LineThrough,
    )
}

@Preview
@Composable
private fun DefaultPreview() {
    DismessTheme {
        Surface(color = palette.surface) {
            InviteFrameImpl({ _: String, _: String -> true }) {}
        }
    }
}
