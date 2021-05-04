package by.dismess.android

import android.content.Context
import by.dismess.android.impl.StorageInterfaceImpl
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
    single<ChatsFrameInterface> { ChatsFrameController() }
    single<DialogFrameInterface> { DialogFrameController() }
    single<FindUserFrameInterface> { FindUserFrameController() }
    single<InviteFrameInterface> { InviteFrameController() }
}
