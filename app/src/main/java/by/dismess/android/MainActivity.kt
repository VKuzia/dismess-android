package by.dismess.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import by.dismess.android.ui.Navigate
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import by.dismess.core.getModulesList
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import java.net.InetSocketAddress

class MainActivity : ComponentActivity() {
    private lateinit var app: App
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modulesList = listOf(
            snappyModule,
            controllersModule,
            coreImplModule,
            demoModule
        ) + getModulesList()
        startKoin {
            androidContext(this@MainActivity)
            modules(modulesList)
        }
        app = get().get()
        runBlocking {
            app.start(InetSocketAddress(1234))
        }
        setContent {
            DismessTheme {
                Surface(color = palette.surface) {
                    Navigate()
                }
            }
        }
    }
}
