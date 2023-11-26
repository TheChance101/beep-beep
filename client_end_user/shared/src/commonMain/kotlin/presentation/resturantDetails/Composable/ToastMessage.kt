package presentation.resturantDetails.Composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.beepbeep.designSystem.ui.composable.BPSnackBar
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ToastMessage(
    modifier: Modifier = Modifier,
    state: Boolean = false,
    message: String = "",
    isError: Boolean = false,
) {
    AnimatedVisibility(
        visible = state,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = modifier
    ) {
        BPSnackBar(
            iconTint = if(isError)  Theme.colors.warning else Theme.colors.success,
            iconBackgroundColor = if(isError)  Theme.colors.warningContainer else Theme.colors.successContainer ,
            icon = if(isError) painterResource(Resources.images.warningIcon) else  painterResource(Resources.images.unread)
        ) {
            Text(
                text = message,
                color = if(isError)  Theme.colors.warning else Theme.colors.success,
                style = Theme.typography.body,
            )
        }
    }
}
