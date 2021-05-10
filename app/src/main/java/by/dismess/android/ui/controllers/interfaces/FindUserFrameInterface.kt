package by.dismess.android.ui.controllers.interfaces

import by.dismess.android.service.model.User

interface FindUserFrameInterface {
    fun refreshHistory()
    fun findUser(username: String): User?
    fun addUser(user: User)
}
