package by.dismess.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import by.dismess.android.ui.Navigate
import by.dismess.android.ui.theming.theme.DismessTheme
import by.dismess.android.ui.theming.theme.palette
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(
                snappyModule,
                coreImplModule,
                controllersModule
            )
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
