package by.dismess.android

import android.content.Context
import by.dismess.android.impl.StorageInterfaceImpl
import by.dismess.android.service.AppInfo
import by.dismess.android.service.DemoStorage
import by.dismess.android.service.model.Chat
import by.dismess.android.ui.controllers.ChatsFrameController
import by.dismess.android.ui.controllers.DialogFrameController
import by.dismess.android.ui.controllers.FindUserFrameController
import by.dismess.android.ui.controllers.InviteFrameController
import by.dismess.android.ui.controllers.interfaces.ChatsFrameInterface
import by.dismess.android.ui.controllers.interfaces.DialogFrameInterface
import by.dismess.android.ui.controllers.interfaces.FindUserFrameInterface
import by.dismess.android.ui.controllers.interfaces.InviteFrameInterface
import by.dismess.core.outer.StorageInterface
import com.snappydb.DBFactory
import org.koin.dsl.module

val snappyModule = module {
    single { DBFactory.open(get<Context>(), "Data") }
}

val coreImplModule = module {
    single<StorageInterface> { StorageInterfaceImpl(get()) }
}

val controllersModule = module {
    single<ChatsFrameInterface> { ChatsFrameController(get(), get()) }
    factory<DialogFrameInterface> { (chat: Chat) -> DialogFrameController(get(), chat) }
    single<FindUserFrameInterface> { FindUserFrameController() }
    single<InviteFrameInterface> { InviteFrameController() }
}

val demoModule = module {
    single { DemoStorage() }
    single { AppInfo() }
    single { App() }
}
