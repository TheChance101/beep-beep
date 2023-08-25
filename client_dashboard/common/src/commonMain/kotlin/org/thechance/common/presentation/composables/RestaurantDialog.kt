package org.thechance.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.restaurant.AddRestaurantDialogUiState
import java.awt.Dimension

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDialog(
    modifier: Modifier = Modifier,
    state: AddRestaurantDialogUiState,
    isVisible: Boolean,
    onCreateClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onRestaurantNameChange: (String) -> Unit,
    onOwnerUserNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onWorkingHourChange: (String) -> Unit,
) {
    Dialog(
        visible = isVisible,
        undecorated = true,
        onCloseRequest = onCancelClicked,
    ) {
        window.minimumSize = Dimension(1176, 664)
        Column(
            modifier
                .background(Theme.colors.surface)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "New Restaurant",
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary
            )
            Row(
                modifier = Modifier.fillMaxSize().padding(top = Theme.dimens.space40),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxHeight().width(350.dp)) {
                    BpTextField(
                        onValueChange = onRestaurantNameChange,
                        text = state.name,
                        label = "Restaurant name",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )

                    BpTextField(
                        onValueChange = onOwnerUserNameChange,
                        text = state.ownerUsername,
                        label = "Owner username",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )

                    BpTextField(
                        onValueChange = onPhoneNumberChange,
                        text = state.phoneNumber,
                        label = "Phone number",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
                        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
                    ) {
                        BpTextField(
                            onValueChange = onWorkingHourChange,
                            text = state.workingHours,
                            modifier = Modifier.weight(1f),
                            label = "Working hours",
                            hint = "1:00"
                        )
                        BpTextField(
                            onValueChange = onWorkingHourChange,
                            text = state.workingHours,
                            modifier = Modifier.weight(1f),
                            label = "",
                            hint = "24:00"
                        )
                    }
                    BpTextField(
                        onValueChange = { },
                        text = state.location,
                        label = "Location",
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.End,
                ) {
                    GoogleMap()
                    Row(
                        modifier = Modifier.fillMaxWidth(0.5f).padding(top = Theme.dimens.space24),
                        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
                    ) {
                        BpOutlinedButton(
                            title = "Cancel",
                            onClick = { onCancelClicked() },
                            modifier = Modifier.width(120.dp)
                        )
                        BpButton(
                            title = "Create",
                            onClick = { onCreateClicked() },
                            modifier = Modifier.width(240.dp)
                        )
                    }
                }
            }
        }
    }
}

