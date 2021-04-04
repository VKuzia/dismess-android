package by.dismess.android.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TextMapForm(key: String, value: String, padding: Dp = 8.dp) {
    Row(modifier = Modifier.fillMaxWidth().padding(padding)) {
        Text(text = "$key:", textAlign = TextAlign.Left)
        Text(text = value, textAlign = TextAlign.Right, modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
fun TextMapFormDefaultPreview() {
    Column {
        TextMapForm(key = "hello", value = "it's me")
        TextMapForm(key = "ip", value = "127.0.0.1")
    }
}
