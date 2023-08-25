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
import org.thechance.common.LocalDimensions
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.presentation.composables.SeatsBar
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
                .padding(top = LocalDimensions.current.space16, start = LocalDimensions.current.space16, end = LocalDimensions.current.space16)
                .shadow(elevation = 5.dp)
                .background(Theme.colors.surface, RoundedCornerShape(LocalDimensions.current.space8))
                .padding(LocalDimensions.current.space24)
        ) {

            Text(
                "Create new Taxi",
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary,
            )

            BpTextField(
                modifier = Modifier.padding(top = LocalDimensions.current.space40),
                label = "Taxi Plate Number",
                onValueChange = onTaxiPlateNumberChange,
                text = state.plateNumber
            )

            BpTextField(
                modifier = Modifier.padding(top = LocalDimensions.current.space24),
                label = "Driver Username",
                onValueChange = onDriverUserNamChange,
                text = state.driverUserName
            )

            BpTextField(
                modifier = Modifier.padding(top = LocalDimensions.current.space24),
                label = "Car Model",
                onValueChange = onCarModelChange,
                text = state.carModel
            )

            Text(
                "Car Color",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = LocalDimensions.current.space24),
            )

            CarColors(
                modifier = Modifier.padding(top = LocalDimensions.current.space16),
                colors = CarColor.values().toList(),
                onSelectColor = { onCarColorSelected(it) },
                selectedCarColor = state.selectedCarColor
            )

            Text(
                "Seats",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = LocalDimensions.current.space24),
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
                iconsSize = LocalDimensions.current.space24,
                iconsPadding = PaddingValues(horizontal = LocalDimensions.current.space8),
                modifier = Modifier.fillMaxWidth().padding(top = LocalDimensions.current.space16),
                onClick = { onSeatsSelected(it) }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = LocalDimensions.current.space40),
                horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.space16)
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