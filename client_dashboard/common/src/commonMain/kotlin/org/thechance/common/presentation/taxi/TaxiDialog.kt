package org.thechance.common.presentation.taxi

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.presentation.composables.SeatsBar
import java.awt.Dimension


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaxiDialog(
    modifier: Modifier = Modifier,
    onTaxiPlateNumberChange: (String) -> Unit,
    onDriverUserNamChange: (String) -> Unit,
    onCarModelChange: (String) -> Unit,
    onCarColorSelected: (CarColor) -> Unit,
    onSeatsSelected: (Int) -> Unit,
    setDialogVisibility: () -> Unit,
    isVisible: Boolean,
    addTaxiDialogUiState: AddTaxiDialogUiState,
    onCreateTaxiClicked: () -> Unit
) {


    Dialog(
        transparent = true,
        visible = isVisible,
        onCloseRequest = setDialogVisibility,
        focusable = true,
        undecorated = true
    ) {


        this.window.minimumSize = Dimension(464, 800)

        Column(
            modifier = modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .shadow(elevation = 5.dp)
                .background(Theme.colors.surface, RoundedCornerShape(Theme.dimens.space8))
                .padding(Theme.dimens.space24)
        ) {

            Text(
                "Create new Taxi",
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary,
            )

            BpTextField(
                modifier = Modifier.padding(top = Theme.dimens.space40),
                label = "Taxi Plate Number",
                onValueChange = onTaxiPlateNumberChange,
                text = addTaxiDialogUiState.taxiPlateNumber
            )

            BpTextField(
                modifier = Modifier.padding(top = Theme.dimens.space24),
                label = "Driver Username",
                onValueChange = onDriverUserNamChange,
                text = addTaxiDialogUiState.driverUserName
            )

            BpTextField(
                modifier = Modifier.padding(top = Theme.dimens.space24),
                label = "Car Model",
                onValueChange = onCarModelChange,
                text = addTaxiDialogUiState.carModel
            )

            Text(
                "Car Color",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = Theme.dimens.space24),
            )

            Colors(
                modifier = Modifier.padding(top = Theme.dimens.space16),
                colors = CarColor.values().toList(),
                onSelectColor = { onCarColorSelected(it) },
                selectedCarColor = addTaxiDialogUiState.selectedCarColor
            )

            Text(
                "Seats",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = Theme.dimens.space24),
            )

            SeatsBar(
                selectedSeatsCount = addTaxiDialogUiState.seats,
                count = 6,
                selectedIcon = painterResource(
                    if (isSystemInDarkTheme()) "ic_filled_seat_dark.svg" else "ic_filled_seat_light.svg"
                ),
                notSelectedIcon = painterResource(
                    if (isSystemInDarkTheme()) "ic_outlined_seat_dark.svg" else "ic_outlined_seat_light.svg"
                ),
                iconsSize = Theme.dimens.space24,
                iconsPadding = PaddingValues(horizontal = Theme.dimens.space8),
                modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
                onClick = { onSeatsSelected(it) }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space40),
                horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space16)
            ) {
                BpOutlinedButton(
                    "Cancel",
                    onClick = setDialogVisibility,
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Colors(
    modifier: Modifier = Modifier,
    colors: List<CarColor>,
    onSelectColor: (CarColor) -> Unit,
    selectedCarColor: CarColor
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(colors) { carColor ->
            val color = Color(carColor.hexadecimal)

            val selectedModifier = if (selectedCarColor == carColor) {
                Modifier.size(32.dp)
                    .border(
                        width = 2.dp,
                        color = Theme.colors.contentSecondary,
                        shape = RoundedCornerShape(4.dp)
                    ).padding(4.dp)

            } else {
                Modifier.size(32.dp).padding(4.dp)
            }
            Box(
                modifier = selectedModifier,
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(20.dp)
                        .background(color, shape = RoundedCornerShape(4.dp))
                        .border(
                            width = 2.dp,
                            color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .onClick { onSelectColor(carColor) }
                )
            }
        }
    }
}

