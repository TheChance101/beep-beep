package presentation.resturantDetails.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BPSnackBar
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun  ToastMessage(
    modifier : Modifier=Modifier,
    state: Boolean=false,
    message:String="",
){
    AnimatedVisibility(
        visible = state,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = modifier
    ) {
        BPSnackBar(
            modifier = Modifier.border(width = 1.dp, color = Theme.colors.contentBorder, shape = RoundedCornerShape(size = Theme.radius.medium)),
            backgroundColor = Theme.colors.secondary,
            iconBackgroundColor = Theme.colors.secondary,
            icon = painterResource(Resources.images.unread)
        ) {
            Text(
                text =  message ,
                color = Theme.colors.primary,
                style = Theme.typography.body,
            )
        }
    }
}
