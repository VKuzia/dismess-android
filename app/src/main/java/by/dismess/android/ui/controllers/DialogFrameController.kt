package by.dismess.android.ui.controllers

import by.dismess.android.App
import by.dismess.android.ui.controllers.interfaces.DialogFrameInterface
import by.dismess.core.chating.ChatManager
import by.dismess.core.chating.elements.Chat
import by.dismess.core.chating.elements.Message
import by.dismess.core.chating.viewing.FlowIterator
import by.dismess.core.events.EventBus
import by.dismess.core.events.MessageEvent
import by.dismess.core.managers.DataManager
import by.dismess.core.utils.UniqID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext.get
import java.util.Date

class DialogFrameController(
    private val chat: Chat,
    private val dataManager: DataManager = get().get(),
    private val chatManager: ChatManager = get().get(),
    private val eventBus: EventBus = get().get(),
    private val app: App = get().get(),
) :
    DialogFrameInterface {
    private lateinit var updateMessageListFun: (Message) -> Unit
    override fun registerMessagesListener(func: (message: Message) -> Unit) {
        updateMessageListFun = func
        eventBus.registerHandler<MessageEvent> { event -> func(event.message) }
    }

    override fun getMessages(): MutableList<Message> = runBlocking {
        val result: MutableList<Message> = mutableListOf()
        val iterator =
            FlowIterator.create(chatManager, chat.otherFlow, chat.otherFlow.lastMessage)
        do {
            result.add(iterator.value!!)
        } while (iterator.previous())
        return@runBlocking result.asReversed()
    }

    override fun sendMessage(text: String) {
        val message = Message(Date(), chat.id, chat.otherID, text)
        GlobalScope.launch {
//            app.network.sendRawMessage(address, message.text.toByteArray())
            chat.sendMessage(message)
        }
        addMessage(message)
    }

    private fun addMessage(message: Message) {
        updateMessageListFun(message)
    }

    override fun refreshHistory() {
//        TODO("Not yet implemented")
    }

    override fun getChatName(): String {
        return chat.id.toString().substring(0, 6) + "..."
    }

    override fun getOwnId(): UniqID = runBlocking {
        return@runBlocking dataManager.getId()
    }
}
