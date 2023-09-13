package presentation.login.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.login.LoginScreenInteractionListener
import presentation.login.PermissionInteractionListener
import resources.Resources

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WrongPermissionBottomSheet(listener: PermissionInteractionListener,
                               modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.wrapContentHeight()
            .padding(horizontal = Theme.dimens.space16, vertical =24.dp)
    ) {
        Icon(
            painter = painterResource(Resources.images.errorIcon),
            tint = Theme.colors.primary,
            contentDescription = null,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = Resources.strings.wrongPermission,
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom =16.dp)
        )
        Text(
            text = Resources.strings.wrongPermissionMessage,
            style = Theme.typography.body,
            color = Theme.colors.contentSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        BpButton(
            onClick = listener::onRequestPermissionClicked,
            title = Resources.strings.requestAPermission,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        BpTransparentButton(
            onClick = listener::onCancelClicked,
            title = Resources.strings.close,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
    }
}