package by.dismess.android.ui.frames

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import by.dismess.android.R
import by.dismess.android.lib.get
import by.dismess.android.service.model.User
import by.dismess.android.ui.controllers.FindUserFrameController
import by.dismess.android.ui.controllers.interfaces.FindUserFrameInterface
import by.dismess.android.ui.helpers.CircularImage
import by.dismess.android.ui.helpers.LineTextField
import by.dismess.android.ui.helpers.TopPanelIconButton
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FindUserFrameImpl(
    controller: FindUserFrameInterface = get(),
    onReturn: () -> Unit
) {
    val searchRunningState = remember { mutableStateOf(false) }
    val lastUserFound = remember { mutableStateOf<User?>(null) }
    val usernameFieldState = remember { mutableStateOf(TextFieldValue()) }
    val coroutineScope = rememberCoroutineScope()
    val onSearchStarted = {
        startSearching(
            searchRunningState,
            usernameFieldState,
            lastUserFound,
            coroutineScope,
            controller::findUser
        )
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopPanel(onReturn)
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            StatusPanel(searchRunningState, lastUserFound, controller::addUser, onReturn)
        }
        LineTextField(fieldState = usernameFieldState, labelText = "Enter username to search")
        Spacer(modifier = Modifier.weight(0.1f))
        Button(onClick = onSearchStarted) { Text("Search") }
        Spacer(modifier = Modifier.weight(0.3f))
        CircularImage(
            R.drawable.search_icon,
            modifier = Modifier.clickable(onClick = onSearchStarted)
        )
        Spacer(modifier = Modifier.weight(0.3f))
    }
}

@Composable
private fun TopPanel(onReturn: () -> Unit) {
    TopAppBar(
        title = { Text("Find user") },
        navigationIcon = {
            TopPanelIconButton(onClick = onReturn, imageVector = Icons.Filled.ArrowBack)
        }
    )
}

@Composable
private fun StatusPanel(
    searchRunningState: MutableState<Boolean>,
    lastFoundUser: MutableState<User?>,
    addUser: (User) -> Unit,
    onReturn: () -> Unit
) {
    if (searchRunningState.value) {
        CircularProgressIndicator()
        return
    }
    if (lastFoundUser.value == null) {
        Text("No results yet", style = MaterialTheme.typography.h4)
    } else {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                lastFoundUser.value!!.displayName,
                style = MaterialTheme.typography.h5,
                color = palette.primary
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Text(
                "Do you want to add this user to the chats list?",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Button(
                onClick = {
                    addUser(lastFoundUser.value!!)
                    onReturn()
                }
            ) { Text("Add User") }
        }
    }
}

private fun startSearching(
    searchRunningState: MutableState<Boolean>,
    usernameFieldState: MutableState<TextFieldValue>,
    lastUserFoundState: MutableState<User?>,
    coroutineScope: CoroutineScope,
    findUser: (String) -> User?
) {
    // Demo. We emulate searching
    coroutineScope.launch {
        searchRunningState.value = true
        delay(2000)
        lastUserFoundState.value = findUser(usernameFieldState.value.text)
        searchRunningState.value = false
    }
}

@Preview
@Composable
private fun FindUserFramePreview() {
    DismessTheme {
        Surface(color = palette.surface) {
            FindUserFrameImpl(FindUserFrameController()) { }
        }
    }
}
