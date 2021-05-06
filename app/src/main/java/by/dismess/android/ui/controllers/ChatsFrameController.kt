package by.dismess.android.ui.controllers

import by.dismess.android.service.AppInfo
import by.dismess.android.service.Chat
import by.dismess.android.service.DemoStorage
import by.dismess.android.ui.controllers.interfaces.ChatsFrameInterface
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
        return storage.ownId.rawID.toString()
    }
}
