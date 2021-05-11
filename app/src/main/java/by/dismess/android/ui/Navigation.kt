package by.dismess.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import by.dismess.android.BuildConfig
import by.dismess.android.lib.get
import by.dismess.android.service.DemoStorage
import by.dismess.android.ui.frames.ChatsFrameImpl
import by.dismess.android.ui.frames.DialogFrameImpl
import by.dismess.android.ui.frames.FindUserFrameImpl
import by.dismess.android.ui.frames.InviteFrameImpl
import by.dismess.core.managers.App
import by.dismess.core.utils.UniqID
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

@Composable
fun Navigate(app: App = GlobalContext.get().get()) {
    val navController = rememberNavController()
    var startDestination = "InviteFrame"
    if (BuildConfig.ADAM) {
        startDestination = "ChatsFrame"
        runBlocking { app.saveInitialData("ADAM") }
    }
    NavHost(navController, startDestination = startDestination) {
        composable("InviteFrame") { InviteFrame(navController) }
        composable("ChatsFrame") { ChatsFrame(navController) }
        composable("DialogFrame/{chosenChat}") { backStackEntry ->
            DialogFrame(navController, backStackEntry.arguments?.getString("chosenChat"))
        }
        composable("FindUserFrame") { FindUserFrame(navController) }
    }
}

@Composable
fun InviteFrame(navController: NavController) {
    InviteFrameImpl { navController.navigate("ChatsFrame") }
}

@Composable
private fun ChatsFrame(navController: NavController) {
    ChatsFrameImpl({ navController.navigate("FindUserFrame") }) { chosenChat ->
        navController.navigate("DialogFrame/${chosenChat.userID}")
    }
}

@Composable
private fun DialogFrame(navController: NavController, chatIdString: String?) {
    if (chatIdString == null) {
        return
    }
    val storage: DemoStorage = get()
    val chatId = UniqID(chatIdString)
    val chat = storage.chats.find { it.userID == chatId }
    DialogFrameImpl(get(null) { parametersOf(chat) }) { navController.navigate("ChatsFrame") }
}

@Composable
private fun FindUserFrame(navController: NavController) {
    FindUserFrameImpl { navController.navigate("ChatsFrame") }
}
