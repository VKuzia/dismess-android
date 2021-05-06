package by.dismess.android.ui.controllers.interfaces

import by.dismess.core.model.Message
import by.dismess.core.model.UserID

interface DialogFrameInterface {
    fun getMessages(): MutableList<Message>
    fun addMessage(message: Message)
    fun refreshHistory()
    fun getChatName(): String
    fun getOwnId(): UserID
}
