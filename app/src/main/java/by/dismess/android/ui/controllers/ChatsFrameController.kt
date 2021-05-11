package by.dismess.android.ui.controllers

import by.dismess.android.service.AppInfo
import by.dismess.android.service.DemoStorage
import by.dismess.android.service.model.Chat
import by.dismess.android.ui.controllers.interfaces.ChatsFrameInterface
import by.dismess.core.network.convertAddressToInvite
import by.dismess.core.network.retrievePublicSocketAddress
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.koin.core.context.GlobalContext.get

class ChatsFrameController(
    private val storage: DemoStorage = get().get(),
    private val appInfo: AppInfo = get().get()
) : ChatsFrameInterface {
    override fun refreshHistory() {
//        TODO("Not yet implemented")
    }

    override fun getChatsList(): MutableList<Chat> {
        return storage.chats
    }

    override fun getAppVersion(): String {
        return appInfo.version
    }

    override fun getUserIdAsString(): String {
        return storage.ownId.toString()
    }

    override suspend fun retrieveInvite(): String? {
        val socketAddress = GlobalScope.async {
            retrievePublicSocketAddress(1234)
        }
        val result = socketAddress.await() ?: return null
        return convertAddressToInvite(result)
    }
}
