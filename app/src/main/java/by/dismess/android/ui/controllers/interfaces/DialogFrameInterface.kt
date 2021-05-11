package by.dismess.android.ui.controllers.interfaces

import by.dismess.core.chating.elements.Message
import by.dismess.core.utils.UniqID

interface DialogFrameInterface {
    fun registerMessagesListener(func: (message: Message) -> Unit)
    fun getMessages(): MutableList<Message>
    fun addMessage(text: String)
    fun refreshHistory()
    fun getChatName(): String
    fun getOwnId(): UniqID
}
