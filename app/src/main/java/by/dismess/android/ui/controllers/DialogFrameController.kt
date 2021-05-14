package by.dismess.android.ui.controllers

import by.dismess.android.App
import by.dismess.android.service.DemoStorage
import by.dismess.android.service.model.Chat
import by.dismess.android.ui.controllers.interfaces.DialogFrameInterface
import by.dismess.core.chating.elements.Message
import by.dismess.core.utils.UniqID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext.get
import java.net.InetSocketAddress
import java.util.Date

class DialogFrameController(
    private val storage: DemoStorage,
    private val chat: Chat,
    private val app: App = get().get(),
) :
    DialogFrameInterface {
    private lateinit var updateMessageListFun: (Message) -> Unit
    override fun registerMessagesListener(func: (message: Message) -> Unit) {
        app.network.setMessageReceiver { _, data ->
            val message = Message(Date(), chat.userID, chat.userID, data.toString())
            chat.messages.add(message)
//            addMessage(message)
            func(message)
        }
    }

    override fun getMessages(): MutableList<Message> {
        return chat.messages
    }

    override fun sendMessage(text: String) {
        val parts = text.split(':')
        val address = InetSocketAddress(parts[0], parts[1].toInt())
        val message = Message(Date(), chat.userID, storage.ownId, text)
        GlobalScope.launch {
            app.network.sendRawMessage(address, message.text.toByteArray())
        }
        chat.messages.add(message)
        addMessage(message)
    }

    private fun addMessage(message: Message) {
        updateMessageListFun(message)
    }

    override fun refreshHistory() {
//        TODO("Not yet implemented")
    }

    override fun getChatName(): String {
        return chat.name
    }

    override fun getOwnId(): UniqID {
        return storage.ownId
    }
}
