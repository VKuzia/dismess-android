package by.dismess.android.ui.controllers

import by.dismess.android.service.AppInfo
import by.dismess.android.ui.controllers.interfaces.ChatsFrameInterface
import by.dismess.core.chating.ChatManager
import by.dismess.core.chating.elements.Chat
import by.dismess.core.managers.DataManager
import by.dismess.core.model.Invite
import by.dismess.core.network.retrievePublicSocketAddress
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext.get

class ChatsFrameController(
    private val chatManager: ChatManager = get().get(),
    private val dataManager: DataManager = get().get(),
    private val appInfo: AppInfo = get().get()
) : ChatsFrameInterface {
    override fun refreshHistory() {
//        TODO("Not yet implemented")
    }

    override fun getChatsList(): MutableList<Chat> {
        return  chatManager.chats.values.toMutableList()
    }

    override fun getAppVersion(): String {
        return appInfo.version
    }

    override fun getUserIdAsString(): String {
        return runBlocking {
            dataManager.getId().toString()
        }
    }

    override suspend fun retrieveInvite(): Invite? {
        val socketAddress = dataManager.getOwnIP()!!
//        val socketAddress = GlobalScope.async {
//            retrievePublicSocketAddress(1234)
//        }
//        val result = socketAddress.await() ?: return null
        return Invite(dataManager.getId(), socketAddress)
    }
}
