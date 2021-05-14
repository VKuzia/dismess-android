package by.dismess.android.ui.controllers.interfaces

interface InviteFrameInterface {
    suspend fun tryEnterSystem(login: String, inviteString: String): Boolean
    fun isValidLogin(login: String): Boolean
    fun isValidInvite(invite: String): Boolean
    fun isRegistered(): Boolean
}
