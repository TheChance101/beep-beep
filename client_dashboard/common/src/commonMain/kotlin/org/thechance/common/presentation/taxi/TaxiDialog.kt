package org.thechance.common.presentation.taxi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaxiDialog(
    isVisible: Boolean,
    onSeatsSelected: (Int) -> Unit,
    onCarModelChange: (String) -> Unit,
    onCarColorSelected: (CarColor) -> Unit,
    onDriverUserNamChange: (String) -> Unit,
    onTaxiPlateNumberChange: (String) -> Unit,

    onCreateTaxiClicked: () -> Unit,
    onCancelCreateTaxiClicked: () -> Unit,

    state: TaxiInfoUiState,
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
                onValueChange = onTaxiPlateNumberChange,
                text = state.plateNumber
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = Resources.Strings.driverUsername,
                onValueChange = onDriverUserNamChange,
                text = state.driverUserName
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = Resources.Strings.carModel,
                onValueChange = onCarModelChange,
                text = state.carModel
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
                onSelectColor = { onCarColorSelected(it) },
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
                onClick = { onSeatsSelected(it) }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
                horizontalArrangement = Arrangement.spacedBy(16.kms)
            ) {

                BpOutlinedButton(
                    Resources.Strings.cancel,
                    onClick = onCancelCreateTaxiClicked,
                    modifier = Modifier.width(120.kms)
                )
                BpButton(
                    title = Resources.Strings.create,
                    onClick = onCreateTaxiClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaxiDialog(
    isVisible: Boolean,
    onSeatsSelected: (Int) -> Unit,
    onCarColorSelected: (CarColor) -> Unit,
    onDriverUserNamChange: (String) -> Unit,
    onTaxiPlateNumberChange: (String) -> Unit,
    onCarModelChange: (String) -> Unit,

    onSaveTaxiClicked: () -> Unit,
    onCancelClicked: () -> Unit,

    state: TaxiInfoUiState,
    modifier: Modifier = Modifier,
) {

    Dialog(
        transparent = true,
        focusable = true,
        undecorated = true,
        visible = isVisible,
        onCloseRequest = onCancelClicked,

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
                onValueChange = onTaxiPlateNumberChange,
                text = state.plateNumber
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = Resources.Strings.driverUsername,
                onValueChange = onDriverUserNamChange,
                text = state.driverUserName
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = Resources.Strings.carModel,
                onValueChange = onCarModelChange,
                text = state.carModel
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
                onSelectColor = { onCarColorSelected(it) },
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
                onClick = { onSeatsSelected(it) }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
                horizontalArrangement = Arrangement.spacedBy(16.kms)
            ) {

                BpOutlinedButton(
                    Resources.Strings.cancel,
                    onClick = onCancelClicked,
                    modifier = Modifier.width(120.kms)
                )
                BpButton(
                    title = Resources.Strings.save,
                    onClick = onSaveTaxiClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaxiDialog(
    isVisible: Boolean,
    actions: TaxiDialogListener,
    isEditMode: Boolean = false,
    state: TaxiInfoUiState,
    modifier: Modifier = Modifier,
) {

    Dialog(
        transparent = true,
        focusable = true,
        undecorated = true,
        visible = isVisible,
        onCloseRequest = actions::onCancelClicked,
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
                onValueChange = actions::onTaxiPlateNumberChange,
                text = state.plateNumber
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = Resources.Strings.driverUsername,
                onValueChange = actions::onDriverUserNamChange,
                text = state.driverUserName
            )

            BpTextField(
                modifier = Modifier.padding(top = 24.kms),
                label = Resources.Strings.carModel,
                onValueChange = actions::onCarModelChanged,
                text = state.carModel
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
                onSelectColor = { actions.onCarColorSelected(it) },
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
                onClick = { actions.onSeatSelected(it) }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
                horizontalArrangement = Arrangement.spacedBy(16.kms)
            ) {

                BpOutlinedButton(
                    Resources.Strings.cancel,
                    onClick = actions::onCancelClicked,
                    modifier = Modifier.width(120.kms)
                )
                BpButton(
                    title = if (isEditMode) Resources.Strings.save else Resources.Strings.create,
                    onClick = if (isEditMode) actions::onSaveClicked else actions::onCreateTaxiClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

//@Composable
//fun TaxiDialog(
//    isVisible: Boolean,
//    onSeatsSelected: (Int) -> Unit,
//    onCarColorSelected: (CarColor) -> Unit,
//    onDriverUserNamChange: (String) -> Unit,
//    onTaxiPlateNumberChange: (String) -> Unit,
//    onCarModelChange: (String) -> Unit,
//
//    onSaveTaxiClicked: () -> Unit,
//    onCancelClicked: () -> Unit,
//
//    state: TaxiInfoUiState,
//    modifier: Modifier = Modifier,
//) {
//
//    Dialog(
//        transparent = true,
//        focusable = true,
//        undecorated = true,
//        visible = isVisible,
//        onCloseRequest = onCancelClicked,
//
//        ) {
//
//        this.window.minimumSize = Dimension(464, 800)
//
//    }
//}
//
//interface DialogContentFactory {
//    @Composable
//    fun createContent(
//        context: DialogContext,
//        actions: TaxiDialogActions
//    )
//}
//
//enum class DialogContext {
//    ADD_TAXI, EDIT_TAXI
//}
//
//class AddTaxiDialogContentFactory : DialogContentFactory {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun createContent(context: DialogContext, actions: TaxiDialogActions) {
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
//            horizontalArrangement = Arrangement.spacedBy(16.kms)
//        ) {
//            BpOutlinedButton(
//                Resources.Strings.cancel,
//                onClick = { actions.onCancelClicked() },
//                modifier = Modifier.width(120.kms)
//            )
//            BpButton(
//                title = Resources.Strings.create,
//                onClick = { actions.onCreateOrSaveTaxiClicked() },
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}
//
//class EditTaxiDialogContentFactory : DialogContentFactory {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun createContent(context: DialogContext, actions: TaxiDialogActions) {
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
//            horizontalArrangement = Arrangement.spacedBy(16.kms)
//        ) {
//            BpOutlinedButton(
//                Resources.Strings.cancel,
//                onClick = { actions.onCancelClicked() },
//                modifier = Modifier.width(120.kms)
//            )
//            BpButton(
//                title = Resources.Strings.save,
//                onClick = { actions.onCreateOrSaveTaxiClicked() },
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}
//
//@Composable
//fun SharedTaxiDialogContent(
//    state: TaxiInfoUiState,
//    actions: TaxiDialogActions,
//) {
//    Text(
//        text = Resources.Strings.createNewTaxi,
//        style = Theme.typography.headlineLarge,
//        color = Theme.colors.contentPrimary,
//    )
//
//    BpTextField(
//        modifier = Modifier.padding(top = 40.kms),
//        label = Resources.Strings.taxiPlateNumber,
//        onValueChange = actions::onPlateNumberChange,
//        text = state.plateNumber
//    )
//
//    BpTextField(
//        modifier = Modifier.padding(top = 24.kms),
//        label = Resources.Strings.driverUsername,
//        onValueChange = actions::onDriverUserNameChange,
//        text = state.driverUserName
//    )
//
//    BpTextField(
//        modifier = Modifier.padding(top = 24.kms),
//        label = Resources.Strings.carModel,
//        onValueChange = actions::onCarModelChange,
//        text = state.carModel
//    )
//
//    Text(
//        text = Resources.Strings.carColor,
//        style = Theme.typography.title,
//        color = Theme.colors.contentPrimary,
//        modifier = Modifier.padding(top = 24.kms),
//    )
//
//    CarColors(
//        modifier = Modifier.padding(top = 16.kms),
//        colors = CarColor.values().toList(),
//        onSelectColor = actions::onCarColorSelected,
//        selectedCarColor = state.selectedCarColor
//    )
//
//    Text(
//        Resources.Strings.seats,
//        style = Theme.typography.title,
//        color = Theme.colors.contentPrimary,
//        modifier = Modifier.padding(top = 24.kms),
//    )
//
//    SeatsBar(
//        selectedSeatsCount = state.seats,
//        count = 6,
//        selectedIcon = painterResource(Resources.Drawable.seatFilled),
//        notSelectedIcon = painterResource(Resources.Drawable.seatOutlined),
//        iconsSize = 24.kms,
//        iconsPadding = PaddingValues(horizontal = 8.kms),
//        modifier = Modifier.fillMaxWidth().padding(top = 16.kms),
//        onClick = { actions.onSeatSelected(it) }
//    )
//}
//
//@Composable
//fun TaxiDialog(
//    isVisible: Boolean,
//    context: DialogContext,
//    actions: TaxiDialogActions,
//    state: TaxiInfoUiState,
//    modifier: Modifier = Modifier,
//    onCancelClicked: () -> Unit,
//) {
//    Dialog(
//        transparent = true,
//        focusable = true,
//        undecorated = true,
//        visible = isVisible,
//        onCloseRequest = onCancelClicked,
//    ) {
//
//        this.window.minimumSize = Dimension(464, 800)
//
//        Column(
//            modifier = modifier
//                .padding(top = 16.kms, start = 16.kms, end = 16.kms)
//                .shadow(elevation = 5.kms)
//                .background(Theme.colors.surface, RoundedCornerShape(8.kms))
//                .padding(24.kms)
//        ) {
//
//            Text(
//                text = Resources.Strings.createNewTaxi,
//                style = Theme.typography.headlineLarge,
//                color = Theme.colors.contentPrimary,
//            )
//
//            BpTextField(
//                modifier = Modifier.padding(top = 40.kms),
//                label = Resources.Strings.taxiPlateNumber,
//                onValueChange = actions::onPlateNumberChange,
//                text = state.plateNumber
//            )
//
//            BpTextField(
//                modifier = Modifier.padding(top = 24.kms),
//                label = Resources.Strings.driverUsername,
//                onValueChange = actions::onDriverUserNameChange,
//                text = state.driverUserName
//            )
//
//            BpTextField(
//                modifier = Modifier.padding(top = 24.kms),
//                label = Resources.Strings.carModel,
//                onValueChange = actions::onCarModelChange,
//                text = state.carModel
//            )
//
//            Text(
//                text = Resources.Strings.carColor,
//                style = Theme.typography.title,
//                color = Theme.colors.contentPrimary,
//                modifier = Modifier.padding(top = 24.kms),
//            )
//
//            CarColors(
//                modifier = Modifier.padding(top = 16.kms),
//                colors = CarColor.values().toList(),
//                onSelectColor = actions::onCarColorSelected,
//                selectedCarColor = state.selectedCarColor
//            )
//
//            Text(
//                Resources.Strings.seats,
//                style = Theme.typography.title,
//                color = Theme.colors.contentPrimary,
//                modifier = Modifier.padding(top = 24.kms),
//            )
//
//            SeatsBar(
//                selectedSeatsCount = state.seats,
//                count = 6,
//                selectedIcon = painterResource(Resources.Drawable.seatFilled),
//                notSelectedIcon = painterResource(Resources.Drawable.seatOutlined),
//                iconsSize = 24.kms,
//                iconsPadding = PaddingValues(horizontal = 8.kms),
//                modifier = Modifier.fillMaxWidth().padding(top = 16.kms),
//                onClick = { actions.onSeatSelected(it) }
//            )
//            when (context) {
//                DialogContext.ADD_TAXI ->
//                    AddTaxiDialogContentFactory().createContent(context, actions)
//
//                DialogContext.EDIT_TAXI ->
//                    EditTaxiDialogContentFactory().createContent(context, actions)
//            }
//        }
//    }
//
//}
//
//interface TaxiDialogActions {
//    fun onCancelClicked()
//    fun onCreateOrSaveTaxiClicked()
//    fun onCarColorSelected(color: CarColor)
//
//    fun onSeatSelected(seats: Int)
//    fun onSaveClicked()
//    fun onPlateNumberChange(number: String)
//    fun onDriverUserNameChange(name: String)
//    fun onCarModelChange(model: String)
//    // Define other actions here
//}
//
//class TaxiDialogBuilder(
//    private val actions: TaxiDialogActions
//) {
//    private var onSaveClicked: (() -> Unit)? = null
//    private var onCancelClicked: (() -> Unit)? = null
//
//    fun onSaveClicked(callback: () -> Unit) {
//        onSaveClicked = callback
//    }
//
//    fun onCancelClicked(callback: () -> Unit) {
//        onCancelClicked = callback
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun Build(
//        isVisible: Boolean,
//        isEditMode: Boolean,
//        state: TaxiInfoUiState,
//        modifier: Modifier = Modifier
//    ) {
//        Dialog(
//            transparent = true,
//            focusable = true,
//            undecorated = true,
//            visible = isVisible,
//            onCloseRequest = onCancelClicked ?: actions::onCancelClicked,
//
//            ) {
//
//            this.window.minimumSize = Dimension(464, 800)
//
//            Column(
//                modifier = modifier
//                    .padding(top = 16.kms, start = 16.kms, end = 16.kms)
//                    .shadow(elevation = 5.kms)
//                    .background(Theme.colors.surface, RoundedCornerShape(8.kms))
//                    .padding(24.kms)
//            ) {
//
//                Text(
//                    text = Resources.Strings.createNewTaxi,
//                    style = Theme.typography.headlineLarge,
//                    color = Theme.colors.contentPrimary,
//                )
//
//                BpTextField(
//                    modifier = Modifier.padding(top = 40.kms),
//                    label = Resources.Strings.taxiPlateNumber,
//                    onValueChange = actions::onPlateNumberChange,
//                    text = state.plateNumber
//                )
//
//                BpTextField(
//                    modifier = Modifier.padding(top = 24.kms),
//                    label = Resources.Strings.driverUsername,
//                    onValueChange = actions::onDriverUserNameChange,
//                    text = state.driverUserName
//                )
//
//                BpTextField(
//                    modifier = Modifier.padding(top = 24.kms),
//                    label = Resources.Strings.carModel,
//                    onValueChange = actions::onCarModelChange,
//                    text = state.carModel
//                )
//
//                Text(
//                    text = Resources.Strings.carColor,
//                    style = Theme.typography.title,
//                    color = Theme.colors.contentPrimary,
//                    modifier = Modifier.padding(top = 24.kms),
//                )
//
//                CarColors(
//                    modifier = Modifier.padding(top = 16.kms),
//                    colors = CarColor.values().toList(),
//                    onSelectColor = actions::onCarColorSelected,
//                    selectedCarColor = state.selectedCarColor
//                )
//
//                Text(
//                    Resources.Strings.seats,
//                    style = Theme.typography.title,
//                    color = Theme.colors.contentPrimary,
//                    modifier = Modifier.padding(top = 24.kms),
//                )
//
//                SeatsBar(
//                    selectedSeatsCount = state.seats,
//                    count = 6,
//                    selectedIcon = painterResource(Resources.Drawable.seatFilled),
//                    notSelectedIcon = painterResource(Resources.Drawable.seatOutlined),
//                    iconsSize = 24.kms,
//                    iconsPadding = PaddingValues(horizontal = 8.kms),
//                    modifier = Modifier.fillMaxWidth().padding(top = 16.kms),
//                    onClick = { actions.onSeatSelected(it) }
//                )
//
//                Row(
//                    modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
//                    horizontalArrangement = Arrangement.spacedBy(16.kms)
//                ) {
//                    BpOutlinedButton(
//                        Resources.Strings.cancel,
//                        onClick = onCancelClicked ?: actions::onCancelClicked,
//                        modifier = Modifier.width(120.kms)
//                    )
//                    BpButton(
//                        title = if (isEditMode) Resources.Strings.save else Resources.Strings.create,
//                        onClick = onSaveClicked ?: actions::onCreateOrSaveTaxiClicked,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//            }
//        }
//
//    }


//@OptIn(ExperimentalMaterial3Api::class)
//class TaxiDialogBuilder(
//    private val title: String,
//    private val isVisible: Boolean,
//    private val onSaveClicked: () -> Unit,
//    private val onCancelClicked: () -> Unit,
//    private val actions: TaxiDialogListener,
//    private val state: TaxiInfoUiState,
//    private val modifier: Modifier = Modifier
//) {
//    @Composable
//    fun build() {
//        Dialog(
//            transparent = true,
//            focusable = true,
//            undecorated = true,
//            visible = isVisible,
//            onCloseRequest = onCancelClicked,
//
//            ) {
//
//            this.window.minimumSize = Dimension(464, 800)
//
//            Column(
//                modifier = modifier
//                    .padding(top = 16.kms, start = 16.kms, end = 16.kms)
//                    .shadow(elevation = 5.kms)
//                    .background(Theme.colors.surface, RoundedCornerShape(8.kms))
//                    .padding(24.kms)
//            ) {
//
//                Text(
//                    text = Resources.Strings.createNewTaxi,
//                    style = Theme.typography.headlineLarge,
//                    color = Theme.colors.contentPrimary,
//                )
//
//                BpTextField(
//                    modifier = Modifier.padding(top = 40.kms),
//                    label = Resources.Strings.taxiPlateNumber,
//                    onValueChange = actions::onPlateNumberChange,
//                    text = state.plateNumber
//                )
//
//                BpTextField(
//                    modifier = Modifier.padding(top = 24.kms),
//                    label = Resources.Strings.driverUsername,
//                    onValueChange = actions::onDriverUserNameChange,
//                    text = state.driverUserName
//                )
//
//                BpTextField(
//                    modifier = Modifier.padding(top = 24.kms),
//                    label = Resources.Strings.carModel,
//                    onValueChange = actions::onCarModelChange,
//                    text = state.carModel
//                )
//
//                Text(
//                    text = Resources.Strings.carColor,
//                    style = Theme.typography.title,
//                    color = Theme.colors.contentPrimary,
//                    modifier = Modifier.padding(top = 24.kms),
//                )
//
//                CarColors(
//                    modifier = Modifier.padding(top = 16.kms),
//                    colors = CarColor.values().toList(),
//                    onSelectColor = { actions.onCarColorSelected(it) },
//                    selectedCarColor = state.selectedCarColor
//                )
//
//                Text(
//                    Resources.Strings.seats,
//                    style = Theme.typography.title,
//                    color = Theme.colors.contentPrimary,
//                    modifier = Modifier.padding(top = 24.kms),
//                )
//
//                SeatsBar(
//                    selectedSeatsCount = state.seats,
//                    count = 6,
//                    selectedIcon = painterResource(Resources.Drawable.seatFilled),
//                    notSelectedIcon = painterResource(Resources.Drawable.seatOutlined),
//                    iconsSize = 24.kms,
//                    iconsPadding = PaddingValues(horizontal = 8.kms),
//                    modifier = Modifier.fillMaxWidth().padding(top = 16.kms),
//                    onClick = { actions.onSeatSelected(it) }
//                )
//
//                Row(
//                    modifier = Modifier.fillMaxWidth().padding(top = 40.kms),
//                    horizontalArrangement = Arrangement.spacedBy(16.kms)
//                ) {
//
//                    BpOutlinedButton(
//                        Resources.Strings.cancel,
//                        onClick = onCancelClicked,
//                        modifier = Modifier.width(120.kms)
//                    )
//                    BpButton(
//                        title = title,
//                        onClick = onSaveClicked,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//            }
//        }
//    }
//}
