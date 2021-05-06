package by.dismess.android.service.model

import by.dismess.core.model.Message
import by.dismess.core.model.UserID
import java.util.Date
import kotlin.collections.ArrayList

// Demo Version only
class Chat(val name: String, id: UserID) {
    val messages = MutableList(15) {
        Message(
            Date(),
            id,
            "some text",
            ArrayList()
        )
    }
}
