package presentation.mealManagement.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCircleImage
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpPriceField
import com.beepbeep.designSystem.ui.composable.BpRoundedImage
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composable.BpAppBar
import presentation.composable.modifier.noRippleEffect
import presentation.mealManagement.MealScreenInteractionListener
import presentation.mealManagement.MealDetails
import presentation.mealManagement.isValid
import presentation.mealManagement.toCuisinesString
import resources.Resources
import util.ImagePicker
import util.rememberBitmapFromBytes

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MealContent(
    meal: MealDetails,
    isLoading: Boolean,
    listener: MealScreenInteractionListener,
    buttonTitle: String,
    imagePicker: ImagePicker,
    screenTitle: String,
    modifier: Modifier = Modifier,
) {
    imagePicker.registerPicker { listener.onImagePicked(it) }
    Column(
        modifier = Modifier.fillMaxSize().background(Theme.colors.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BpAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.surface)
                .border(width = 1.dp, color = Theme.colors.divider, shape = RectangleShape),
            onNavigateUp = listener::onClickBack,
            title = screenTitle
        )

        Column(
            modifier = modifier
                .padding(Theme.dimens.space16)
                .background(Theme.colors.surface, shape = RoundedCornerShape(Theme.radius.medium))
                .padding(Theme.dimens.space16),
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (meal.imageUrl.isEmpty() || meal.image != null) {
                BpRoundedImage(
                    modifier = Modifier.sizeIn(minHeight = 120.dp).fillMaxWidth(),
                    bitmap = rememberBitmapFromBytes(meal.image),
                    placeholder = painterResource(Resources.images.galleryAdd),
                    onClick = { imagePicker.pickImage() }
                )
            } else {
                BpRoundedImage(
                    modifier = Modifier.sizeIn(minHeight = 120.dp).fillMaxWidth(),
                    painter = rememberAsyncImagePainter(meal.imageUrl),
                    editPainter = painterResource(Resources.images.iconEdit),
                    onClick = { imagePicker.pickImage() }
                )
            }

            BpTextField(
                text = meal.name,
                onValueChange = listener::onNameChange,
                label = Resources.strings.name,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )

            BpExpandableTextField(
                text = meal.description,
                onValueChange = listener::onDescriptionChange,
                label = Resources.strings.description,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )

            BpPriceField(
                text = meal.price,
                onValueChange = listener::onPriceChange,
                label = Resources.strings.price,
                keyboardType = KeyboardType.Decimal,
                currency = meal.currency,
                flag = painterResource(getFlag(meal.currency)),
                modifier = Modifier.fillMaxWidth(),
                imageModifier = Modifier.size(width = 12.dp, height = 10.dp)
            )

            CuisineTextField(
                text = meal.mealCuisines.toCuisinesString(),
                label = Resources.strings.cuisines,
                modifier = Modifier.fillMaxWidth().noRippleEffect(listener::onCuisineClick),
            )

            if (buttonTitle != "Save") {
                BpButton(
                    onClick = listener::onAddMeal,
                    title = buttonTitle,
                    isLoading = isLoading,
                    enabled = meal.isValid() && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                BpButton(
                    onClick = listener::onUpdateMeal,
                    title = buttonTitle,
                    isLoading = isLoading,
                    enabled = meal.isValid() && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
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

            Icon(
                painterResource(Resources.images.edit),
                contentDescription = null,
                tint = Theme.colors.contentPrimary
            )
        }
    }
}

@Composable
private fun getFlag(currency: String): String {
    return when (currency) {
        "EGP" -> Resources.images.flagEgypt
        "IQD" -> Resources.images.flagIraq
        "SYP" -> Resources.images.flagSyria
        "ILS" -> Resources.images.flagPalestine
        else -> Resources.images.flag
    }
}
