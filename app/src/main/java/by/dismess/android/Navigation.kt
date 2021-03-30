package by.dismess.android

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import by.dismess.android.frames.ChatsFrameImpl
import by.dismess.android.frames.InviteFrameImpl

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "InviteFrame") {
        composable("InviteFrame") { InviteFrame(navController) }
        composable("ChatsFrame") { ChatsFrame() }
    }
}

@Composable
fun InviteFrame(navController: NavController) {
    InviteFrameImpl { navController.navigate("ChatsFrame") }
}

@Composable
fun ChatsFrame() {
    ChatsFrameImpl()
}