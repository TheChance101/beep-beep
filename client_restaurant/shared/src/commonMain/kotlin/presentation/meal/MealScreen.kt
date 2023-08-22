package presentation.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCircleImage
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpPriceField
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import resources.Resources

class MealScreen :
    BaseScreen<MealScreenModel, MealScreenUIState, MealScreenUIEffect, MealScreenInteractionListener>() {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { MealScreenModel() }
        initScreen(screenModel)
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: MealScreenUIState, listener: MealScreenInteractionListener) {
        Column(
            Modifier.fillMaxSize().background(Theme.colors.surface).padding(16.dp)
                .widthIn(max = 300.dp),
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            BpCircleImage(
                modifier = Modifier.sizeIn(minHeight = 104.dp, minWidth = 104.dp),
                painter = painterResource(Resources.images.galleryAdd),
                onClick = listener::onImageClicked
            )

            BpTextField(
                text = state.name,
                onValueChange = listener::onNameChanged,
                label = Resources.strings.name,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )

            BpExpandableTextField(
                text = state.description,
                onValueChange = listener::onDescriptionChanged,
                label = Resources.strings.description,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )

            BpPriceField(
                text = state.price,
                onValueChange = listener::onPriceChanged,
                label = Resources.strings.price,
                keyboardType = KeyboardType.Decimal,
                currency = state.currency,
                flag = painterResource(Resources.images.sort),
                modifier = Modifier.fillMaxWidth(),
            )

            CuisineTextField(
                text = state.cuisines.toCuisinesString(),
                label = Resources.strings.cuisines,
                modifier = Modifier.fillMaxWidth().clickable { listener.onCuisineClick() },
            )


            BpButton(
                onClick = listener::onClickAddMeal,
                title = Resources.strings.buttonAdd,
                enabled = state.isAddEnable,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    override fun onEffect(effect: MealScreenUIEffect, navigator: Navigator) {
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
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = label,
            modifier = Modifier.padding(bottom = Theme.dimens.space8),
            style = Theme.typography.title,
            color = Theme.colors.contentPrimary
        )

        Row(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Theme.colors.divider,
                    shape = RoundedCornerShape(Theme.radius.medium)
                )
                .padding(horizontal = Theme.dimens.space16)
                .fillMaxWidth()
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