package org.thechance.common.presentation.taxi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import java.awt.Dimension


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaxiDialog(
    modifier: Modifier = Modifier,
    onTaxiPlateNumberChange: (String) -> Unit,
    onDriverUserNamChange: (String) -> Unit,
    onCarModelChange: (String) -> Unit,
    onCarColorSelected: (CarColor) -> Unit,
    onSeatsSelected: (Int) -> Unit,
    setDialogVisibility: () -> Unit,
    isVisible: Boolean,
    selectedCarColor: CarColor,
) {

    Dialog(
        visible = isVisible,
        onCloseRequest = setDialogVisibility,
        focusable = true,
        undecorated = true
    ) {
        this.window.title = "Create new Taxi"
        this.window.minimumSize = Dimension(464, 680)
        this.window.background = convertComposeColorToJavaSwingWindow(Theme.colors.background)

        Column(modifier = modifier.padding(Theme.dimens.space24)) {

            Text(
                "Create new Taxi",
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary,
            )

            BpTextField(
                modifier = Modifier.padding(vertical = 16.dp),
                label = "Taxi Plate Number",
                onValueChange = onTaxiPlateNumberChange,
                text = ""
            )

            BpTextField(
                modifier = Modifier.padding(vertical = 16.dp),
                label = "Driver Username",
                onValueChange = onDriverUserNamChange,
                text = ""
            )

            BpTextField(
                modifier = Modifier.padding(vertical = 16.dp),
                label = "Car Model",
                onValueChange = onCarModelChange,
                text = ""
            )

            Text(
                "Car Color",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(vertical = 16.dp),
            )

            Colors(
                colors = CarColor.values().toList(),
                onSelectColor = { onCarColorSelected(it) },
                selectedCarColor = selectedCarColor
            )

            Text(
                "Seats",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
            )
            Seats()

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
                    onClick = {},
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
            val color = when (carColor) {
                CarColor.RED -> Color(0xffF47373)
                CarColor.YELLOW -> Color(0xffF8EC7E)
                CarColor.GREEN -> Color(0xff80E5AB)
                CarColor.BLUE -> Color(0xff77DEEE)
                CarColor.WHITE -> Color(0xffFFFFFF)
                CarColor.GREY -> Color(0xffAFAFAF)
                CarColor.BLACK -> Color(0xff3F3F3F)

            }
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

@Composable
private fun Seats(
    modifier: Modifier = Modifier,
    selectedSeats: Int = 4,
    maxSeats: Int = 6
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(maxSeats) { position ->
            Icon(
                painterResource(
                    if (position < selectedSeats) {
                        "fill_seat.svg"
                    } else {
                        "seat.svg"
                    }
                ),
                contentDescription = null,
                tint = Theme.colors.contentSecondary
            )
        }
    }
}

private fun convertComposeColorToJavaSwingWindow(color: Color): java.awt.Color {
    val argb = color.toArgb()
    return java.awt.Color(argb)
}