package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.restaurant.RestaurantInformationUIState
import org.thechance.common.presentation.restaurant.RestaurantInteractionListener
import org.thechance.common.presentation.restaurant.RestaurantUiState
import org.thechance.common.presentation.util.kms
import java.awt.Dimension

@Composable
fun NewRestaurantInfoDialog(
    state: RestaurantUiState,
    listener: RestaurantInteractionListener,
    modifier: Modifier = Modifier,
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
        state = state.restaurantInformationUIState,
        onCreateClicked = listener::onCreateNewRestaurantClicked,
        onLocationChange = listener::onLocationChange,
        onUpdateRestaurantClicked = listener::onUpdateRestaurantClicked,
        isEditMode = state.isEditMode
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantDialog(
    state: RestaurantInformationUIState,
    isVisible: Boolean,
    onCreateClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onRestaurantNameChange: (String) -> Unit,
    onOwnerUserNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onWorkingStartHourChange: (String) -> Unit,
    onWorkingEndHourChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onUpdateRestaurantClicked : (String) -> Unit,
    isEditMode: Boolean,
    modifier: Modifier = Modifier,
) {
    Dialog(
        visible = isVisible,
        undecorated = true,
        onCloseRequest = onCancelClicked,
        resizable = false,
    ) {
        window.minimumSize = Dimension(1100, 664)
        Column(
            modifier.fillMaxSize()
                .background(Theme.colors.surface)
                .border(
                    1.kms,
                    Theme.colors.divider,
                    RoundedCornerShape(Theme.radius.medium)
                )
                .padding(24.kms)

        ) {
            Text(
                text = if (isEditMode) Resources.Strings.updateRestaurant else Resources.Strings.newRestaurant,
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary
            )
            Row(
                modifier = Modifier.fillMaxSize().padding(top = Theme.dimens.space40),
                horizontalArrangement = Arrangement.spacedBy(24.kms)
            ) {
                Column(modifier = Modifier.fillMaxHeight().weight(1f)) {
                    BpTextField(
                        onValueChange = onRestaurantNameChange,
                        text = state.name,
                        label = Resources.Strings.restaurantName,
                        hint = "",
                        errorMessage = state.nameError?.errorMessage ?: "",
                        isError = state.nameError?.isError ?: false
                    )

                    BpTextField(
                        onValueChange = onOwnerUserNameChange,
                        text = state.ownerUsername,
                        label = Resources.Strings.ownerUsername,
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = "",
                        errorMessage = state.userNameError?.errorMessage ?: "",
                        isError = state.userNameError?.isError ?: false
                    )

                    BpTextField(
                        onValueChange = onPhoneNumberChange,
                        text = state.phoneNumber,
                        label = Resources.Strings.phoneNumber,
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = "",
                        errorMessage = state.phoneNumberError?.errorMessage ?: "",
                        isError = state.phoneNumberError?.isError ?: false
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
                            errorMessage = state.startTimeError?.errorMessage ?: "",
                            isError = state.startTimeError?.isError ?: false
                        )
                        BpTextField(
                            onValueChange = onWorkingEndHourChange,
                            text = state.closingTime,
                            modifier = Modifier.weight(1f),
                            label = "",
                            hint = Resources.Strings.workEndHourHint,
                            errorMessage = state.endTimeError?.errorMessage ?: "",
                            isError = state.endTimeError?.isError ?: false
                        )
                    }
                    BpTextField(
                        onValueChange = onLocationChange,
                        text = state.location,
                        label = Resources.Strings.location,
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = "",
                        errorMessage = state.locationError?.errorMessage ?: "",
                        isError = state.locationError?.isError ?: false
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight().weight(2f),
                    horizontalAlignment = Alignment.End,
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxWidth().weight(5f),
                        lat = state.latitude, lng = state.longitude,
                    ) { address ->
                        onLocationChange(address)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.kms).weight(1f),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        BpOutlinedButton(
                            title = Resources.Strings.cancel,
                            onClick = onCancelClicked,
                            modifier = Modifier.padding(end = Theme.dimens.space16).width(120.kms)
                        )
                        BpButton(
                            title = if (isEditMode) Resources.Strings.update else Resources.Strings.create,
                            onClick = {
                                if (isEditMode) {
                                    onUpdateRestaurantClicked(state.id)
                                } else {
                                    onCreateClicked()
                                }
                            },
                            modifier = Modifier.width(300.dp),
                            enabled = state.buttonEnabled
                        )
                    }
                }
            }
        }
    }
}

