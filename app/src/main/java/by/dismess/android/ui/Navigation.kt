package by.dismess.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import by.dismess.android.ui.frames.ChatsFrameImpl
import by.dismess.android.ui.frames.DialogFrameImpl
import by.dismess.android.ui.frames.FindUserFrameImpl
import by.dismess.android.ui.frames.InviteFrameImpl

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "InviteFrame") {
        composable("InviteFrame") { InviteFrame(navController) }
        composable("ChatsFrame") { ChatsFrame(navController) }
        composable("DialogFrame/{chatName}") { backStackEntry ->
            DialogFrame(navController, backStackEntry.arguments?.getString("chatName"))
        }
        composable("FindUserFrame") { FindUserFrame(navController) }
    }
}

@Composable
fun InviteFrame(navController: NavController) {
    InviteFrameImpl({ _: String, _: String -> true }) { navController.navigate("ChatsFrame") }
}

private val exampleOfChatsList = Array(30) { it.toString() }

@Composable
private fun ChatsFrame(navController: NavController) {
    ChatsFrameImpl(
        exampleOfChatsList,
        {
            navController.navigate("FindUserFrame")
        }
    ) { chosenChatName ->
        navController.navigate("DialogFrame/$chosenChatName")
    }
}

private val exampleOfMessages = MutableList(5) { it.toString() }

@Composable
private fun DialogFrame(navController: NavController, chatName: String?) {
    DialogFrameImpl(chatName.toString(), exampleOfMessages) {
        navController.navigate("ChatsFrame")
    }
}

@Composable
private fun FindUserFrame(navController: NavController) {
    FindUserFrameImpl({ true }, {}) {
        navController.navigate("ChatsFrame")
    }
}
