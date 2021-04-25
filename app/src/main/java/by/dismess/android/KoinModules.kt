package by.dismess.android

import android.content.Context
import by.dismess.android.impl.StorageInterfaceImpl
import by.dismess.core.outer.StorageInterface
import com.snappydb.DBFactory
import org.koin.dsl.module

val snappyModule = module {
    single { DBFactory.open(get<Context>(), "Data") }
}

val coreImplModule = module {
    single<StorageInterface> { StorageInterfaceImpl(get()) }
}
