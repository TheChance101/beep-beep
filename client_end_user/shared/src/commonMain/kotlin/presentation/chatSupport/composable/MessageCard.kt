package presentation.chatSupport.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.chatSupport.MessageUIState

@Composable
fun MessageCard(message: MessageUIState) {
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = if (message.isMe) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = message.message,
            style = Theme.typography.body,
            color = Theme.colors.contentSecondary,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = Theme.radius.medium,
                        topEnd = Theme.radius.medium,
                        bottomStart = if (message.isMe) Theme.radius.medium else 0.dp,
                        bottomEnd = if (!message.isMe) Theme.radius.medium else 0.dp
                    )
                )
                .background(
                    if (message.isMe) Theme.colors.surface else Theme.colors.pink
                )
                .border(
                    1.dp, color = Theme.colors.divider, shape = RoundedCornerShape(
                        topStart = Theme.radius.medium,
                        topEnd = Theme.radius.medium,
                        bottomStart = if (message.isMe) Theme.radius.medium else 0.dp,
                        bottomEnd = if (!message.isMe) Theme.radius.medium else 0.dp
                    )
                )
                .padding(16.dp)

        )
    }
}