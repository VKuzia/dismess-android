package by.dismess.android.service.model

import by.dismess.core.chating.elements.Message
import by.dismess.core.utils.UniqID
import java.util.Date
import kotlin.collections.ArrayList

// Demo Version only
class Chat(val name: String, val userID: UniqID) {
    val messages = MutableList(15) {
        Message(
            Date(),
            userID,
            userID,
            "some text",
            ArrayList()
        )
    }
}
