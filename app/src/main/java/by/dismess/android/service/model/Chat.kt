package by.dismess.android.service.model

import by.dismess.core.model.Message
import by.dismess.core.model.UserID
import java.util.Date
import kotlin.collections.ArrayList

// Demo Version only
class Chat(val name: String, val userID: UserID) {
    val messages = MutableList(15) {
        Message(
            Date(),
            userID,
            "some text",
            ArrayList()
        )
    }
}
