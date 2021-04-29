package by.dismess.android.ui.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import by.dismess.android.ui.theming.theme.BackgroundColor
import by.dismess.android.ui.theming.theme.DismessTheme

@Composable
fun FindUserFrameImpl(
    findUser: (String) -> Boolean,
    addUser: (String) -> Unit,
    onReturn: () -> Unit
) {
    val lastNameFoundState = remember { mutableStateOf<String?>(null) }
    Column {
        TopPanel(onReturn)
        TextPanel(findUser) { lastNameFoundState.value = it }
        StatusPanel(lastNameFoundState, addUser, onReturn)
    }
}

@Composable
private fun TopPanel(onReturn: () -> Unit) {
    TopAppBar(
        title = { Text("Find user") },
        navigationIcon = {
            IconButton(onClick = onReturn) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Composable
private fun TextPanel(
    findUser: (String) -> Boolean,
    onUserFound: (String?) -> Unit
) {
    val searchFieldState = remember { mutableStateOf(TextFieldValue()) }
    Row {
        TextField(
            value = searchFieldState.value,
            onValueChange = { searchFieldState.value = it }
        )
        Button(
            onClick = {
                if (findUser(searchFieldState.value.text)) {
                    onUserFound(searchFieldState.value.text)
                } else {
                    onUserFound(null)
                }
            }
        ) { Text("Search") }
    }
}

@Composable
private fun StatusPanel(
    lastFoundUsername: MutableState<String?>,
    addUser: (String) -> Unit,
    onReturn: () -> Unit
) {
    if (lastFoundUsername.value == null) {
        Text("No results yet")
    } else {
        Column {
            Text(
                "User ${lastFoundUsername.value} does exist!\n " +
                    "Do you want to add this user to the chats list?"
            )
            Button(
                onClick = {
                    addUser(lastFoundUsername.value!!)
                    onReturn()
                }
            ) { Text("Add User") }
        }
    }
}

@Preview
@Composable
private fun FindUserFramePreview() {
    DismessTheme(true) {
        Surface(color = BackgroundColor) {
            FindUserFrameImpl(findUser = { true }, addUser = { }) { }
        }
    }
}
