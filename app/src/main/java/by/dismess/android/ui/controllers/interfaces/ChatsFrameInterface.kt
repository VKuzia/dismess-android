package by.dismess.android.ui.controllers.interfaces

import by.dismess.android.service.Chat

interface ChatsFrameInterface {
    fun refreshHistory()
    fun getChatsList(): MutableList<Chat>
    fun getAppVersion(): String
    fun getUserIdAsString(): String
}
