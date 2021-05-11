package by.dismess.android.ui.controllers.interfaces

import by.dismess.android.service.model.Chat
import by.dismess.core.model.Invite

interface ChatsFrameInterface {
    fun refreshHistory()
    fun getChatsList(): MutableList<Chat>
    fun getAppVersion(): String
    fun getUserIdAsString(): String
    suspend fun retrieveInvite(): Invite?
}
