package by.dismess.android.ui.controllers

import by.dismess.android.service.DemoStorage
import by.dismess.android.service.model.Chat
import by.dismess.android.ui.controllers.interfaces.DialogFrameInterface
import by.dismess.core.model.Message
import by.dismess.core.model.UserID

class DialogFrameController(private val storage: DemoStorage, private val chat: Chat) :
    DialogFrameInterface {
    override fun getMessages(): MutableList<Message> {
        return chat.messages
    }

    override fun addMessage(message: Message) {
        chat.messages.add(message)
    }

    override fun refreshHistory() {
//        TODO("Not yet implemented")
    }

    override fun getChatName(): String {
        return chat.name
    }

    override fun getOwnId(): UserID {
        return storage.ownId
    }
}
