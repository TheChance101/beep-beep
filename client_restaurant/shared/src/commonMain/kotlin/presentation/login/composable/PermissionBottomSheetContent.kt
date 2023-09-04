package presentation.login.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.login.LoginScreenInteractionListener
import presentation.login.LoginScreenUIState
import resources.Resources


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionBottomSheetContent(
    listener: LoginScreenInteractionListener,
    modifier: Modifier = Modifier,
    state: LoginScreenUIState
) {

    Column(
        modifier = modifier.wrapContentHeight()
            .padding(vertical = Theme.dimens.space24).padding(horizontal = Theme.dimens.space16)
    ) {
        Text(
            text = Resources.strings.askForPermission,
            color = Theme.colors.contentPrimary,
            style = Theme.typography.headlineLarge,
        )
        BpTextField(
            text = state.restaurantName,
            onValueChange = listener::onRestaurantNameChanged,
            label = Resources.strings.name,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpTextField(
            text = state.ownerEmail,
            onValueChange = listener::onOwnerEmailChanged,
            label = Resources.strings.name,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpExpandableTextField(
            text = state.description,
            onValueChange = listener::onDescriptionChanged,
            label = Resources.strings.description,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpButton(
            onClick = {
                listener.onSubmitClicked(
                    state.restaurantName,
                    state.ownerEmail,
                    state.description
                )
            },
            title = Resources.strings.submit,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpTransparentButton(
            onClick = listener::onCancelClicked,
            title = Resources.strings.cancel,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
    }
}