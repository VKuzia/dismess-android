package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import by.dismess.android.lib.get
import by.dismess.android.ui.controllers.InviteFrameController
import by.dismess.android.ui.controllers.interfaces.InviteFrameInterface
import by.dismess.android.ui.helpers.LineTextField
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val INVALID_DATA_STATUS_MESSAGE = "Invalid input data.\n Please, try again."
private const val ENTER_ERROR = "Couldn't enter the system due to unsolvable reasons."
private const val INVALID_TEXT_MESSAGE = "Invalid Field"

@Composable
fun InviteFrameImpl(controller: InviteFrameInterface = get(), navigateToChats: () -> Unit) {
    val loginFieldState = remember { mutableStateOf(TextFieldValue()) }
    val inviteFieldState = remember { mutableStateOf(TextFieldValue()) }
    val loginErrorState = remember { mutableStateOf<String?>(null) }
    val inviteErrorState = remember { mutableStateOf<String?>(null) }
    val statusState = remember { mutableStateOf<String?>(null) }
    val runningValidationState = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greet()
        // Is needed to preserve space for StatusPanel
        // Otherwise TextFields will move on status change
        Box(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .wrapContentSize()
        ) {
            StatusPanel(runningValidationState, statusState)
        }
        LineTextField(loginFieldState, "Enter your login", errorState = loginErrorState)
        LineTextField(inviteFieldState, "Enter service invite", errorState = inviteErrorState)
        Button(
            onClick = {
                val loginValidationResult =
                    validateTextField(loginFieldState, loginErrorState, controller::isValidLogin)
                val inviteValidationResult =
                    validateTextField(inviteFieldState, inviteErrorState, controller::isValidInvite)
                if (!loginValidationResult || !inviteValidationResult) {
                    statusState.value = INVALID_DATA_STATUS_MESSAGE
                } else {
//                    statusState.value = null
                    tryEnter(
                        controller,
                        runningValidationState,
                        statusState,
                        loginFieldState.value.text,
                        inviteFieldState.value.text,
                        coroutineScope,
                        navigateToChats
                    )
                }
            }
        ) {
            Text("Proceed")
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.2f))
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
        textDecoration = TextDecoration.LineThrough
    )
}

@Composable
fun StatusPanel(
    runningValidationState: MutableState<Boolean>,
    statusState: MutableState<String?>
) {
    if (runningValidationState.value) {
        CircularProgressIndicator()
    } else {
        if (statusState.value != null) {
            Text(
                text = statusState.value!!,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun validateTextField(
    fieldState: MutableState<TextFieldValue>,
    errorState: MutableState<String?>,
    validator: (String) -> Boolean
): Boolean {
    return if (!validator(fieldState.value.text)) {
        errorState.value = INVALID_TEXT_MESSAGE
        false
    } else {
        errorState.value = null
        true
    }
}

private fun tryEnter(
    controller: InviteFrameInterface,
    runningValidationState: MutableState<Boolean>,
    statusState: MutableState<String?>,
    login: String,
    invite: String,
    coroutineScope: CoroutineScope,
    onEntered: () -> Unit
) {
    // Demo. We emulate hard work of validation.
    coroutineScope.launch {
        runningValidationState.value = true
        delay(2000)
        val result = controller.tryEnterSystem(login, invite)
        runningValidationState.value = false
        if (result) {
            onEntered()
        } else {
            statusState.value = ENTER_ERROR
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    DismessTheme {
        Surface(color = palette.surface) {
            InviteFrameImpl(InviteFrameController()) {}
        }
    }
}
