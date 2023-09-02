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

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalResourceApi::class
)
@Composable
fun WrongPermissionBottomSheet(
    listener: LoginScreenInteractionListener,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentHeight()
            .padding(bottom = Theme.dimens.space24, top = Theme.dimens.space32).padding(horizontal = Theme.dimens.space16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Resources.images.errorIcon),
            tint = Theme.colors.primary,
            contentDescription = null,
            modifier = Modifier.padding(bottom = Theme.dimens.space24),
        )

        Text(
            text = Resources.strings.wrongPermission,
            modifier = Modifier.padding(bottom = Theme.dimens.space16),
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary
        )
        Text(
            text = Resources.strings.wrongPermissionMessage,
            modifier = Modifier.padding(bottom = Theme.dimens.space24),
            style = Theme.typography.body,
            textAlign = TextAlign.Center,
            color = Theme.colors.contentSecondary
        )

        BpButton(
            onClick = listener::onRequestPermissionClicked,
            title = Resources.strings.requestAPermission,
            modifier = Modifier.fillMaxWidth().padding(bottom = Theme.dimens.space16),
        )
        BpTransparentButton(
            onClick = listener::onCancelClicked,
            title = Resources.strings.cancel,
            modifier = Modifier.fillMaxWidth(),
        )
    }

}