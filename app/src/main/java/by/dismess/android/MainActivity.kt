package by.dismess.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import by.dismess.android.ui.Navigate
import by.dismess.android.ui.theming.theme.DismessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DismessTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigate()
                }
            }
        }
    }
}
