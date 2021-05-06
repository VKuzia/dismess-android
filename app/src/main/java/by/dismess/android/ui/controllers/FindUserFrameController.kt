package by.dismess.android.ui.controllers

import by.dismess.android.service.model.User
import by.dismess.android.ui.controllers.interfaces.FindUserFrameInterface
import by.dismess.core.model.UserID
import java.math.BigInteger

class FindUserFrameController : FindUserFrameInterface {
    override fun refreshHistory() {
//        TODO("Not yet implemented")
    }

    override fun findUser(username: String): User? {
        return User(username, "Karasik", UserID(BigInteger.ZERO))
    }

    override fun addUser(user: User) {
        //
    }
}
