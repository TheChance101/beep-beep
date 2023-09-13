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
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.login.LoginScreenUIState
import presentation.login.PermissionInteractionListener
import resources.Resources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestPermissionBottomSheet(
    listener: PermissionInteractionListener,
    state: LoginScreenUIState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentHeight()
            .padding(horizontal = Theme.dimens.space16, vertical = 24.dp)
    ) {
        Text(
            text = Resources.strings.askForPermission,
            color = Theme.colors.contentPrimary,
            style = Theme.typography.titleLarge,
        )
        BpTextField(
            text = state.deliveryUsername,
            label = Resources.strings.restaurantName,
            keyboardType = KeyboardType.Text,
            onValueChange = listener::onRestaurantNameChanged,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
        )
        BpTextField(
            text = state.ownerEmail,
            onValueChange = listener::onOwnerEmailChanged,
            label = Resources.strings.userEmailLabel,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        )
        BpExpandableTextField(
            text = state.description,
            onValueChange = listener::onDescriptionChanged,
            label = Resources.strings.whyBeepBeep,
            hint = Resources.strings.questionHint,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        )
        BpButton(
            onClick = {
                listener.onSubmitClicked(
                    restaurantName = state.deliveryUsername,
                    ownerEmail = state.ownerEmail,
                    description = state.description
                )
            },
            title = Resources.strings.submit,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space24),
        )
        BpTransparentButton(
            onClick = listener::onCancelClicked,
            title = Resources.strings.cancel,
            modifier = Modifier.fillMaxWidth().padding(vertical = Theme.dimens.space16),
        )
    }
}