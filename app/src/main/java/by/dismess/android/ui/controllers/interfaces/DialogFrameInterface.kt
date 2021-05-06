package by.dismess.android.ui.controllers.interfaces

import by.dismess.core.model.Message
import by.dismess.core.model.UserID

interface DialogFrameInterface {
    fun registerMessagesListener(func: (message: Message) -> Unit)
    fun getMessages(): MutableList<Message>
    fun addMessage(text: String)
    fun refreshHistory()
    fun getChatName(): String
    fun getOwnId(): UserID
}
