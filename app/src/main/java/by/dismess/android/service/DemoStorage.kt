package by.dismess.android.service

import by.dismess.android.service.model.Chat
import by.dismess.android.service.model.User
import by.dismess.core.utils.UniqID

class DemoStorage {
    val chats = MutableList(10) { Chat(it.toString(), UniqID((it + 1).toString())) }
    val ownId = UniqID("0")

    fun addChat(user: User) {
        chats.add(Chat(user.displayName, user.id))
    }
}
