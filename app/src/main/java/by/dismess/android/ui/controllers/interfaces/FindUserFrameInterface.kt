package by.dismess.android.ui.controllers.interfaces

import by.dismess.core.model.User

interface FindUserFrameInterface {
    fun refreshHistory()
    fun findUser(username: String): User?
    fun addUser(user: User)
}
