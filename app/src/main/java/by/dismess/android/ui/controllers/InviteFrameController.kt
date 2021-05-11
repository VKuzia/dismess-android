package by.dismess.android.ui.controllers

import by.dismess.android.ui.controllers.interfaces.InviteFrameInterface
import by.dismess.core.managers.App
import by.dismess.core.model.Invite
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext

class InviteFrameController(private val api: App = GlobalContext.get().get()) : InviteFrameInterface {

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
        var result: Boolean
        runBlocking {
            result = api.isRegistered()
        }
        return result
    }
}
