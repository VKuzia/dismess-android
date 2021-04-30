package by.dismess.android.ui.helpers

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun LineTextField(
    fieldState: MutableState<TextFieldValue>,
    labelText: String,
    modifier: Modifier = Modifier
) {
    val localFocusManager = LocalFocusManager.current
    TextField(
        value = fieldState.value,
        onValueChange = { fieldState.value = it },
        label = { Text(labelText) },
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
        modifier = modifier.fillMaxWidth(0.7f)
    )
}

@Composable
fun BooleanToast(toastShowState: MutableState<Boolean>, text: String) {
    if (toastShowState.value) {
        Toast.makeText(LocalContext.current, text, Toast.LENGTH_LONG)
            .show()
        toastShowState.value = false
    }
}
