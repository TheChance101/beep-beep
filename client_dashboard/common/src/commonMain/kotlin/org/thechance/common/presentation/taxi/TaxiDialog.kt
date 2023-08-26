package org.thechance.common.presentation.taxi

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.presentation.composables.SeatsBar
import org.thechance.common.presentation.util.kms
import java.awt.Dimension


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaxiDialog(
    isVisible: Boolean,
    onSeatsSelected: (Int) -> Unit,
    onCreateTaxiClicked: () -> Unit,
    onCancelCreateTaxiClicked: () -> Unit,
    onCarModelChange: (String) -> Unit,
    onCarColorSelected: (CarColor) -> Unit,
    onDriverUserNamChange: (String) -> Unit,
    onTaxiPlateNumberChange: (String) -> Unit,
    state: AddTaxiDialogUiState,
    modifier: Modifier = Modifier,
) {

    Dialog(
        transparent = true,
        focusable = true,
        undecorated = true,
        visible = isVisible,
        onCloseRequest = onCancelCreateTaxiClicked,

        ) {

        this.window.minimumSize = Dimension(464, 800)

        Column(
            modifier = modifier
                .padding(top = 16.kms, start = 16.kms, end = 16.kms)
                .shadow(elevation = 5.dp)
                .background(Theme.colors.surface, RoundedCornerShape(8.kms))
                .padding(24.kms)
        ) {

            Text(
                "Create new Taxi",
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary,
            )

            BpTextField(
                modifier = Modifier.padding(top = 40.kms),
                label = "Taxi Plate Number",
                onValueChange = onTaxiPlateNumberChange,
                text = state.plateNumber
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = "Driver Username",
                onValueChange = onDriverUserNamChange,
                text = state.driverUserName
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = "Car Model",
                onValueChange = onCarModelChange,
                text = state.carModel
            )

            Text(
                "Car Color",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = 24.kms),
            )

            CarColors(
                modifier = Modifier.padding(top = 16.kms),
                colors = CarColor.values().toList(),
                onSelectColor = { onCarColorSelected(it) },
                selectedCarColor = state.selectedCarColor
            )

            Text(
                "Seats",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = 24.kms),
            )

            SeatsBar(
                selectedSeatsCount = state.seats,
                count = 6,
                selectedIcon = painterResource(
                    if (isSystemInDarkTheme()) "ic_filled_seat_dark.svg" else "ic_filled_seat_light.svg"
                ),
                notSelectedIcon = painterResource(
                    if (isSystemInDarkTheme()) "ic_outlined_seat_dark.svg" else "ic_outlined_seat_light.svg"
                ),
                iconsSize = 24.kms,
                iconsPadding = PaddingValues(horizontal = 8.kms),
                modifier = Modifier.fillMaxWidth().padding(top = 16.kms),
                onClick = { onSeatsSelected(it) }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
                horizontalArrangement = Arrangement.spacedBy(16.kms)
            ) {

                BpOutlinedButton(
                    "Cancel",
                    onClick = onCancelCreateTaxiClicked,
                    modifier = Modifier.width(120.dp)
                )
                BpButton(
                    title = "Create",
                    onClick = onCreateTaxiClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}