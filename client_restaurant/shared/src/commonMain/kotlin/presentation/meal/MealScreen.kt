package presentation.meal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpCircleImage
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpPriceField
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.CustomBottomSheet
import presentation.composable.ModalBottomSheetState
import presentation.composable.modifier.noRippleEffect
import util.ImagePicker
import util.ImagePickerFactory
import util.getPlatformContext
import util.rememberBitmapFromBytes
import resources.Resources

class MealScreen(private val mealId: String? = null) :
    BaseScreen<MealScreenModel, MealUIState, MealScreenUIEffect, MealScreenInteractionListener>() {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { MealScreenModel(mealId) }
        initScreen(screenModel)
    }

    @Composable
    override fun onRender(state: MealUIState, listener: MealScreenInteractionListener) {
        val sheetState = remember { ModalBottomSheetState() }
        Column(modifier = Modifier.fillMaxSize()) {
            BpAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surface)
                    .border(width = 1.dp, color = Theme.colors.divider, shape = RectangleShape),
                onNavigateUp = { listener.onClickBack() },
                title = if (mealId == null) {
                    Resources.strings.addNewMeal
                } else {
                    Resources.strings.editMeal
                }
            )

            CustomBottomSheet(
                sheetContent = {
                    CuisineBottomSheetContent(
                        cuisines = state.cuisines,
                        onCancelClick = { sheetState.dismiss() },
                        onSaveClick = {
                            listener.onSaveCuisineClick()
                            sheetState.dismiss()
                        },
                        onItemSelected = listener::onCuisineSelected
                    )
                },
                sheetBackgroundColor = Theme.colors.background,
                sheetState = sheetState,
            ) {
                MealScreenContent(state, listener, sheetState)
            }
        }
    }


    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    private fun MealScreenContent(
        state: MealUIState,
        listener: MealScreenInteractionListener,
        sheetState: ModalBottomSheetState,
        imagePicker: ImagePicker = ImagePickerFactory(context = getPlatformContext()).createPicker(),
        modifier: Modifier = Modifier,
    ) {
        imagePicker.registerPicker { listener.onImagePicked(it) }

        Column(
            modifier = modifier.fillMaxSize().background(Theme.colors.background)
                .padding(Theme.dimens.space16)
                .widthIn(max = 300.dp),
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (state.imageUrl.isEmpty()) {
                BpCircleImage(
                    modifier = Modifier.sizeIn(minHeight = 104.dp, minWidth = 104.dp),
                    bitmap = rememberBitmapFromBytes(state.image),
                    placeholder = painterResource(Resources.images.galleryAdd),
                    onClick = { imagePicker.pickImage() }
                )
            } else {
                BpCircleImage(
                    imageSize = 104.dp,
                    modifier = Modifier.sizeIn(minHeight = 104.dp, minWidth = 104.dp),
                    painter = rememberAsyncImagePainter(state.imageUrl),
                    onClick = { imagePicker.pickImage() }
                )
            }

            BpTextField(
                text = state.name,
                onValueChange = listener::onNameChange,
                label = Resources.strings.name,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )

            BpExpandableTextField(
                text = state.description,
                onValueChange = listener::onDescriptionChange,
                label = Resources.strings.description,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )

            BpPriceField(
                text = state.price,
                onValueChange = listener::onPriceChange,
                label = Resources.strings.price,
                keyboardType = KeyboardType.Decimal,
                currency = state.currency,
                flag = painterResource(Resources.images.flag),
                modifier = Modifier.fillMaxWidth(),
            )

            CuisineTextField(
                text = state.selectedCuisines.toCuisinesString(),
                label = Resources.strings.cuisines,
                modifier = Modifier.fillMaxWidth().noRippleEffect {
                    listener.onCuisineClick()
                    sheetState.show()
                },
            )

            BpButton(
                onClick = listener::onAddMeal,
                title = mealId?.let { Resources.strings.save } ?: Resources.strings.add,
                enabled = state.isAddEnable,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CuisineBottomSheetContent(
        cuisines: List<CuisineUIState>,
        onSaveClick: () -> Unit,
        onCancelClick: () -> Unit,
        onItemSelected: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        LazyColumn(
            modifier = modifier.fillMaxWidth().heightIn(max = 320.dp)
                .padding(top = Theme.dimens.space16).padding(horizontal = Theme.dimens.space16)
        ) {

            stickyHeader {
                Row(
                    modifier = Modifier.fillMaxWidth().background(Theme.colors.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(Resources.strings.chooseCuisine, style = Theme.typography.titleLarge)

                    Spacer(Modifier.weight(1f))

                    BpTransparentButton(title = Resources.strings.cancel, onClick = onCancelClick)

                    BpOutlinedButton(
                        title = Resources.strings.save,
                        onClick = onSaveClick,
                        modifier = Modifier.heightIn(max = 32.dp),
                        textPadding = PaddingValues(Theme.dimens.space4)
                    )

                }
            }

            items(cuisines) { cuisine ->
                BpCheckBox(
                    label = cuisine.name,
                    onCheck = { onItemSelected(cuisine.id) },
                    isChecked = cuisine.isSelected,
                    modifier = Modifier.padding(vertical = Theme.dimens.space4)
                )
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun CuisineTextField(
        label: String,
        text: String,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier, horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = label,
                modifier = Modifier.padding(bottom = Theme.dimens.space8),
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )

            Row(
                modifier = Modifier.border(
                    width = 2.dp,
                    color = Theme.colors.divider,
                    shape = RoundedCornerShape(Theme.radius.medium)
                ).padding(horizontal = Theme.dimens.space16).fillMaxWidth()
                    .heightIn(min = 56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    maxLines = 1,
                    style = Theme.typography.body.copy(Theme.colors.contentPrimary)
                )

                Icon(painterResource(Resources.images.edit), contentDescription = null)
            }
        }
    }

    override fun onEffect(effect: MealScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is MealScreenUIEffect.Back -> navigator.pop()
            is MealScreenUIEffect.MealResponseSuccessfully -> navigator.pop()
        }
    }
}
