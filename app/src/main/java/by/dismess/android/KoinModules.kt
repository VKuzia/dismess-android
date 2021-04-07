package by.dismess.android

import android.content.Context
import com.snappydb.DBFactory
import org.koin.dsl.module

val snappyModule = module {
    single { DBFactory.open(get<Context>(), "Data") }
}
