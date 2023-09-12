package org.thechance.common.presentation.taxi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.presentation.composables.CarColors
import org.thechance.common.presentation.composables.SeatsBar
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms
import java.awt.Dimension


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaxiDialog(
    isVisible: Boolean,
    listener: TaxiDialogListener,
    isEditMode: Boolean = false,
    state: TaxiInfoUiState,
    modifier: Modifier = Modifier,
) {

    Dialog(
        transparent = true,
        focusable = true,
        undecorated = true,
        visible = isVisible,
        onCloseRequest = listener::onCancelClicked,
    ) {

        this.window.minimumSize = Dimension(464, 800)

        Column(
            modifier = modifier
                .padding(top = 16.kms, start = 16.kms, end = 16.kms)
                .shadow(elevation = 5.kms)
                .background(Theme.colors.surface, RoundedCornerShape(8.kms))
                .padding(24.kms)
        ) {

            Text(
                text = Resources.Strings.createNewTaxi,
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary,
            )

            BpTextField(
                modifier = Modifier.padding(top = 40.kms),
                label = Resources.Strings.taxiPlateNumber,
                onValueChange = listener::onTaxiPlateNumberChange,
                text = state.plateNumber,
                errorMessage = state.plateNumberError.errorMessage,
                isError = state.plateNumberError.isError
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = Resources.Strings.driverUsername,
                onValueChange = listener::onDriverUserNamChange,
                text = state.driverUserName,
                errorMessage = state.driverUserNameError.errorMessage,
                isError = state.driverUserNameError.isError
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = Resources.Strings.carModel,
                onValueChange = listener::onCarModelChanged,
                text = state.carModel,
                errorMessage = state.carModelError.errorMessage,
                isError = state.carModelError.isError
            )

            Text(
                text = Resources.Strings.carColor,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = 24.kms),
            )

            CarColors(
                modifier = Modifier.padding(top = 16.kms),
                colors = CarColor.values().toList(),
                onSelectColor = { listener.onCarColorSelected(it) },
                selectedCarColor = state.selectedCarColor
            )

            Text(
                Resources.Strings.seats,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = 24.kms),
            )

            SeatsBar(
                selectedSeatsCount = state.seats,
                count = 6,
                selectedIcon = painterResource(Resources.Drawable.seatFilled),
                notSelectedIcon = painterResource(Resources.Drawable.seatOutlined),
                iconsSize = 24.kms,
                iconsPadding = PaddingValues(horizontal = 8.kms),
                modifier = Modifier.fillMaxWidth().padding(top = 16.kms),
                onClick = { listener.onSeatSelected(it) }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
                horizontalArrangement = Arrangement.spacedBy(16.kms)
            ) {

                BpOutlinedButton(
                    Resources.Strings.cancel,
                    onClick = listener::onCancelClicked,
                    modifier = Modifier.width(120.kms)
                )
                BpButton(
                    title = if (isEditMode) Resources.Strings.save else Resources.Strings.create,
                    onClick = if (isEditMode) listener::onSaveClicked else listener::onCreateTaxiClicked,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.isFormValid
                )
            }
        }
    }
}

