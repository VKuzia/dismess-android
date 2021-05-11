package by.dismess.android.ui.controllers

import AppImpl
import by.dismess.android.ui.controllers.interfaces.InviteFrameInterface
import by.dismess.core.model.Invite
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext.get

class InviteFrameController(private val api: AppImpl = get().get()) : InviteFrameInterface {

    override suspend fun tryEnterSystem(login: String, inviteString: String): Boolean {
        val invite = Invite.create(inviteString) ?: return false
        api.register(login, invite)
        return true
    }

    override fun isValidLogin(login: String): Boolean {
        // TODO("Not yet implemented")
        return true
    }

    override fun isValidInvite(invite: String): Boolean {

        return true
    }

    override fun isRegistered(): Boolean {
        var result = false
        runBlocking {
            result = api.isRegistered()
        }
        return result
    }
}
