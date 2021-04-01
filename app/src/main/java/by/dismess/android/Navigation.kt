package by.dismess.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import by.dismess.android.frames.ChatsFrameImpl
import by.dismess.android.frames.DialogFrameImpl
import by.dismess.android.frames.InviteFrameImpl

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "InviteFrame") {
        composable("InviteFrame") { InviteFrame(navController) }
        composable("ChatsFrame") { ChatsFrame(navController) }
        composable("DialogFrame/{chatName}") { backStackEntry ->
            DialogFrame(navController, backStackEntry.arguments?.getString("chatName"))
        }
    }
}

@Composable
fun InviteFrame(navController: NavController) {
    InviteFrameImpl { navController.navigate("ChatsFrame") }
}

val exampleOfChatsList = Array(30) { it.toString() }

@Composable
fun ChatsFrame(navController: NavController) {
    ChatsFrameImpl(exampleOfChatsList) { chosenChatName ->
        navController.navigate("DialogFrame/$chosenChatName")
    }
}

@Composable
fun DialogFrame(navController: NavController, chatName: String?) {
    DialogFrameImpl({ navController.navigate("ChatsFrame") }, chatName.toString())
}
