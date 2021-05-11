package by.dismess.android.ui.controllers

import by.dismess.android.service.DemoStorage
import by.dismess.android.service.model.User
import by.dismess.android.ui.controllers.interfaces.FindUserFrameInterface
import by.dismess.core.utils.UniqID
import org.koin.core.context.GlobalContext.get

class FindUserFrameController(private val storage: DemoStorage = get().get()) :
    FindUserFrameInterface {
    override fun refreshHistory() {
//        TODO("Not yet implemented")
    }

    override fun findUser(username: String): User? {
        return User(username, "Karasik", UniqID("0"))
    }

    override fun addUser(user: User) {
        storage.addChat(user)
    }
}
