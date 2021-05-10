package by.dismess.android.service

import by.dismess.android.service.model.Chat
import by.dismess.android.service.model.User
import by.dismess.core.model.UserID
import java.math.BigInteger

class DemoStorage {
    val chats = MutableList(10) { Chat(it.toString(), UserID((it + 1).toBigInteger())) }
    val ownId = UserID(BigInteger.ZERO)

    fun addChat(user: User) {
        chats.add(Chat(user.displayName, user.id))
    }
}
