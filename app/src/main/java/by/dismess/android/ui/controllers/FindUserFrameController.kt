package by.dismess.android.ui.controllers

import by.dismess.android.ui.controllers.interfaces.FindUserFrameInterface
import by.dismess.core.chating.ChatManager
import by.dismess.core.extensions.id
import by.dismess.core.managers.UserManager
import by.dismess.core.model.User
import by.dismess.core.utils.generateUserID
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext.get

class FindUserFrameController(
    private val userManager: UserManager = get().get(),
    private val chatManager: ChatManager = get().get(),
) :
    FindUserFrameInterface {
    override fun refreshHistory() {
//        TODO("Not yet implemented")
    }

    override fun findUser(username: String): User? = runBlocking {
        userManager.retrieveUser(generateUserID(username))
    }

    override fun addUser(user: User) = runBlocking {
        chatManager.startChat(user.id)
        Unit
    }
}
