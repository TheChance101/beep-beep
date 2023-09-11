package presentation.login.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.delay
import util.BPToastDuration
import kotlin.time.Duration

@Composable
fun BpToast(
    icon: Painter,
    isVisible : Boolean,
    modifier: Modifier = Modifier,
    iconTint: Color = Theme.colors.primary,
    iconBackgroundColor: Color = Theme.colors.hover,
    backgroundColor: Color = Theme.colors.surface,
    duration: BPToastDuration = BPToastDuration.Indefinite,
    content: @Composable () -> Unit
) {
    var isToShow by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isToShow && isVisible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Theme.dimens.space16,
                    vertical = Theme.dimens.space24
                )
                .clip(RoundedCornerShape(Theme.radius.medium))
                .background(backgroundColor)
                .padding(
                    horizontal = Theme.dimens.space16,
                    vertical = Theme.dimens.space8
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)
        ) {
            Icon(
                modifier = Modifier.background(
                    color = iconBackgroundColor,
                    shape = RoundedCornerShape(Theme.radius.medium)
                ).padding(Theme.dimens.space8),
                painter = icon,
                contentDescription = null,
                tint = iconTint
            )
            content()
        }
    }

    LaunchedEffect(key1 = null) {
        when (duration) {
            BPToastDuration.Long -> delay(10000)
            BPToastDuration.Short -> delay(4000)
            BPToastDuration.Indefinite -> delay(Duration.INFINITE)
        }
        isToShow = false
    }
}