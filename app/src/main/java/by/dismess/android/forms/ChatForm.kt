package by.dismess.android.forms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatForm(chatName: String, onClick: (String) -> Unit, lastMessage: String = "...") {
    Card(
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(3.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = { onClick(chatName) })
    ) {
        Column {
            Text(chatName, modifier = Modifier.padding(6.dp), fontSize = 24.sp)
            Text(lastMessage, modifier = Modifier.padding(6.dp))
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    ChatForm("Some chat", {}, "Last message")
}
