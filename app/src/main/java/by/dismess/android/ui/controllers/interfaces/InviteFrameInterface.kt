package by.dismess.android.ui.controllers.interfaces

interface InviteFrameInterface {
    fun tryEnterSystem(login: String, invite: String): Boolean
    fun isValidLogin(login: String): Boolean
    fun isValidInvite(invite: String): Boolean
}
