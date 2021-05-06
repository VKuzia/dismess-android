package by.dismess.android.service

import by.dismess.core.model.UserID
import java.math.BigInteger

class DemoStorage {
    val chats = MutableList(10) { Chat(it.toString(), UserID((it + 1).toBigInteger())) }
    val ownId = UserID(BigInteger.ZERO)
}
