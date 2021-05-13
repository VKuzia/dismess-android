package by.dismess.android.ui.controllers

import by.dismess.android.ui.controllers.interfaces.InviteFrameInterface
import by.dismess.core.dht.DHT
import by.dismess.core.managers.App
import by.dismess.core.model.Invite
import by.dismess.core.utils.generateUserID
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext

class InviteFrameController(
    private val api: App = GlobalContext.get().get(),
    private val dht: DHT = GlobalContext.get().get()
) : InviteFrameInterface {

    override suspend fun tryEnterSystem(login: String, inviteString: String): Boolean {
        val invite = Invite.create(inviteString) ?: return false
        if (!dht.isValidLogin(invite.address, login)) {
            return false
        }
        api.register(login, invite)
        dht.initSelf(generateUserID(login), invite.address)
        return true
    }

    private val base64Regex = Regex("^[a-zA-Z0-9\\/\\+]+$")

    override fun isValidLogin(login: String): Boolean {
        return base64Regex.matches(login)
    }

    override fun isValidInvite(invite: String): Boolean {
        return base64Regex.matches(invite)
    }

    override fun isRegistered(): Boolean {
        var result: Boolean
        runBlocking {
            result = api.isRegistered()
        }
        return result
    }
}
