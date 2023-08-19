package org.thechance.common.presentation.taxi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import java.awt.Dimension


@Composable
fun addTaxi(
    modifier: Modifier = Modifier,
    onTaxiPlateNumberChange: (String) -> Unit,
    setShowDialog: (Boolean) -> Unit
) {
    val carColors = listOf(Color.Gray, Color.Red, Color.DarkGray, Color.Cyan)
    var carColor by remember { mutableStateOf(carColors.first()) }

    Dialog(
        visible = true,
        onCloseRequest = { setShowDialog(false) },
        focusable = true,
    ) {
        this.window.title = "Create new Taxi"
        this.window.minimumSize = Dimension(464, 680)
        this.window.background = convertComposeColorToJavaSwingWindow(Theme.colors.background)

        Column(modifier = modifier.padding(16.dp)) {

            Text(
                "Create new Taxi",
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary,
            )

            Spacer(Modifier.height(24.dp))

            BpTextField(
                modifier = Modifier.padding(vertical = 16.dp),
                label = "Taxi Plate Number",
                onValueChange = onTaxiPlateNumberChange,
                text = ""
            )

            BpTextField(
                modifier = Modifier.padding(vertical = 16.dp),
                label = "Driver Username",
                onValueChange = onTaxiPlateNumberChange,
                text = ""
            )

            BpTextField(
                modifier = Modifier.padding(vertical = 16.dp),
                label = "Car Model",
                onValueChange = onTaxiPlateNumberChange,
                text = ""
            )

            Text(
                "Car Color",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(vertical = 16.dp),
            )

            Colors(
                colors = carColors,
                onSelectColor = { carColor = it },
                selectColor = carColor
            )

            Text(
                "Seats",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
            )
            Seats()

        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Colors(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    onSelectColor: (Color) -> Unit,
    selectColor: Color
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(colors) { carColor ->
            val selectedModifier = if (selectColor == carColor) {
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
                        .background(carColor, shape = RoundedCornerShape(4.dp))
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