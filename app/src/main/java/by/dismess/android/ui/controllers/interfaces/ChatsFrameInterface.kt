package by.dismess.android.ui.controllers.interfaces

import by.dismess.core.chating.elements.Chat
import by.dismess.core.model.Invite

interface ChatsFrameInterface {
    fun refreshHistory()
    fun getChatsList(): MutableList<Chat>
    fun getAppVersion(): String
    fun getUserIdAsString(): String
    suspend fun retrieveInvite(): Invite?
}
