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
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.login.LoginScreenInteractionListener
import resources.Resources

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WrongPermissionBottomSheet(listener: LoginScreenInteractionListener,
                               modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.wrapContentHeight()
            .padding(horizontal = Theme.dimens.space16, vertical = Theme.dimens.space24)
    ) {
        Icon(
            painter = painterResource(Resources.images.errorIcon),
            tint = Theme.colors.primary,
            contentDescription = null,
            modifier = Modifier.padding(bottom = Theme.dimens.space24)
        )
        Text(
            text = Resources.strings.wrongPermission,
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = Theme.dimens.space16)
        )
        Text(
            text = Resources.strings.wrongPermissionMessage,
            style = Theme.typography.body,
            color = Theme.colors.contentSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = Theme.dimens.space24)
        )
        BpButton(
            onClick = listener::onRequestPermissionClicked,
            title = Resources.strings.requestAPermission,
            modifier = Modifier.fillMaxWidth().padding(bottom = Theme.dimens.space16)
        )
        BpTransparentButton(
            onClick = listener::onCancelClicked,
            title = Resources.strings.cancel,
            modifier = Modifier.fillMaxWidth()
        )
    }
}