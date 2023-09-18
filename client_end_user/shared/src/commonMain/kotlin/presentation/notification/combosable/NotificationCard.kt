package presentation.notification.combosable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.Time

@Composable
fun NotificationCard(
    onClickNotification: () -> Unit = {},
    title: String,
    content: String,
    time: Time,
    modifier: Modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
    showDate: Boolean = false,
    date: String = "",
    isClickable: Boolean = false,
    clickableText: String = ""
) {
    val cardModifier = if (isClickable) modifier.clickable { onClickNotification() } else modifier
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Theme.colors.surface),
        modifier = cardModifier
    ) {
        AnimatedVisibility(showDate) {
            Row(
                modifier = Modifier.background(Theme.colors.primary).fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = date,
                    style = Theme.typography.title,
                    color = Theme.colors.onPrimary
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            text = title,
            style = Theme.typography.title,
            color = Theme.colors.contentPrimary
        )
        val text = buildAnnotatedString {
            pushStyle(SpanStyle(color = Theme.colors.contentSecondary))
            append(content)
            pop()
            if (isClickable) {
                pushStyle(SpanStyle(Theme.colors.primary))
                append(" $clickableText")
            }
            toAnnotatedString()
        }
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = text,
            style = Theme.typography.caption,
            color = Theme.colors.contentSecondary
        )

        var hourOfDay = time.hours
        val minute = time.minutes
        val period: String

        if (time.hours < 12) {
            period = "AM"
            if (hourOfDay == 0) {
                hourOfDay = 12
            }
        } else {
            period = "PM"
            if (hourOfDay > 12) {
                hourOfDay -= 12
            }
        }

        val hourString = if (hourOfDay < 10) "0$hourOfDay" else hourOfDay.toString()
        val minuteString = if (minute < 10) "0$minute" else minute.toString()
        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = "$hourString:$minuteString $period",
            style = Theme.typography.caption,
            color = Theme.colors.contentTertiary
        )

    }
}