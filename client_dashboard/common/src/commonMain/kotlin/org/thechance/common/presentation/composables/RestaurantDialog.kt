package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.restaurant.NewRestaurantInfoUiState
import org.thechance.common.presentation.restaurant.RestaurantInteractionListener
import org.thechance.common.presentation.restaurant.RestaurantUiState
import org.thechance.common.presentation.util.kms
import java.awt.Dimension

@Composable
fun NewRestaurantInfoDialog(
    modifier: Modifier = Modifier,
    state: RestaurantUiState,
    listener: RestaurantInteractionListener,
) {
    RestaurantDialog(
        modifier = modifier,
        onRestaurantNameChange = listener::onRestaurantNameChange,
        isVisible = state.isNewRestaurantInfoDialogVisible,
        onCancelClicked = listener::onCancelCreateRestaurantClicked,
        onOwnerUserNameChange = listener::onOwnerUserNameChange,
        onPhoneNumberChange = listener::onPhoneNumberChange,
        onWorkingStartHourChange = listener::onWorkingStartHourChange,
        onWorkingEndHourChange = listener::onWorkingEndHourChange,
        state = state.newRestaurantInfoUiState,
        onCreateClicked = listener::onCreateNewRestaurantClicked,
        onLocationChange = listener::onLocationChange,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantDialog(
    modifier: Modifier = Modifier,
    state: NewRestaurantInfoUiState,
    isVisible: Boolean,
    onCreateClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onRestaurantNameChange: (String) -> Unit,
    onOwnerUserNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onWorkingStartHourChange: (String) -> Unit,
    onWorkingEndHourChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
) {
    Dialog(
        visible = isVisible,
        undecorated = true,
        onCloseRequest = onCancelClicked,
        resizable = false,
    ) {
        window.minimumSize = Dimension(1176, 664)
        Column(
            modifier
                .background(Theme.colors.surface)
                .fillMaxSize()
                .padding(24.kms)
        ) {
            Text(
                text = Resources.Strings.newRestaurant,
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary
            )
            Row(
                modifier = Modifier.fillMaxSize().padding(top = Theme.dimens.space40),
                horizontalArrangement = Arrangement.spacedBy(16.kms)
            ) {
                Column(modifier = Modifier.fillMaxHeight().width(350.kms)) {
                    BpTextField(
                        onValueChange = onRestaurantNameChange,
                        text = state.name,
                        label = Resources.Strings.restaurantName,
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = "",
                        errorMessage = state.nameError.errorMessage,
                        isError = state.nameError.isError
                    )

                    BpTextField(
                        onValueChange = onOwnerUserNameChange,
                        text = state.ownerUsername,
                        label = Resources.Strings.ownerUsername,
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = "",
                        errorMessage = state.userNameError.errorMessage,
                        isError = state.userNameError.isError
                    )

                    BpTextField(
                        onValueChange = onPhoneNumberChange,
                        text = state.phoneNumber,
                        label = Resources.Strings.phoneNumber,
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = "",
                        errorMessage = state.phoneNumberError.errorMessage,
                        isError = state.phoneNumberError.isError
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
                        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
                    ) {
                        BpTextField(
                            onValueChange = onWorkingStartHourChange,
                            text = state.openingTime,
                            modifier = Modifier.weight(1f),
                            label = Resources.Strings.workingHours,
                            hint = Resources.Strings.workStartHourHint,
                            errorMessage = state.startTimeError.errorMessage,
                            isError = state.startTimeError.isError
                        )
                        BpTextField(
                            onValueChange = onWorkingEndHourChange,
                            text = state.closingTime,
                            modifier = Modifier.weight(1f),
                            label = "",
                            hint = Resources.Strings.workEndHourHint,
                            errorMessage = state.endTimeError.errorMessage,
                            isError = state.endTimeError.isError
                        )
                    }
                    BpTextField(
                        onValueChange = onLocationChange,
                        text = state.location,
                        label = Resources.Strings.location,
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = "",
                        errorMessage = state.locationError.errorMessage,
                        isError = state.locationError.isError
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.End,
                ) {
                    GoogleMap(lat = state.lat, lng = state.lng,) { address ->
                        onLocationChange(address)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(0.5f).padding(top = Theme.dimens.space24),
                        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
                    ) {
                        BpOutlinedButton(
                            title = Resources.Strings.cancel,
                            onClick =  onCancelClicked,
                            modifier = Modifier.width(120.kms)
                        )
                        BpButton(
                            title = Resources.Strings.create,
                            onClick =  onCreateClicked,
                            modifier = Modifier.width(240.kms),
                            enabled = state.buttonEnabled
                        )
                    }
                }
            }
        }
    }
}

