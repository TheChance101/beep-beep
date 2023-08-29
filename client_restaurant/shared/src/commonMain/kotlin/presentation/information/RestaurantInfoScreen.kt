package presentation.information

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.BpCard
import presentation.composable.BpRating
import presentation.composable.BpTitleWithContentSection
import presentation.login.LoginScreen
import resources.Resources

class RestaurantInfoScreen : BaseScreen<
        RestaurantInformationScreenModel,
        RestaurantInformationUiState,
        RestaurantInformationUiEffect,
        RestaurantInformationInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }


    @Composable
    override fun onRender(
        state: RestaurantInformationUiState,
        listener: RestaurantInformationInteractionListener
    ) {

        val scrollState = rememberScrollState()

        Column {
            BpAppBar(
                onNavigateUp = { listener.onClickBackArrow() },
                title = Resources.strings.restaurantInfoInSingleLine,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surface)
                    .border(width = 1.dp, color = Theme.colors.divider, shape = RectangleShape)
            )
            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .wrapContentSize()
                    .background(Theme.colors.background)
            ) {
                val informationState = state.restaurant
                RestaurantInfoCard(
                    informationState.ownerUsername,
                    informationState.address,
                    informationState.rating,
                    informationState.priceLevel
                )

                RestaurantUpdateInformationCard(state, listener)

                LogoutCard(listener::onClickLogout)
            }
        }
    }

    override fun onEffect(effect: RestaurantInformationUiEffect, navigator: Navigator) {
        when (effect) {
            is RestaurantInformationUiEffect.NavigateToLogin -> navigator.replaceAll(LoginScreen())
            is RestaurantInformationUiEffect.NavigateUp -> navigator.pop()
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun RestaurantInfoCard(
        ownerUsername: String,
        address: String,
        rating: Double,
        priceLevel: String
    ) {
        BpCard(
            modifier = Modifier.fillMaxWidth().padding(
                start = Theme.dimens.space16,
                end = Theme.dimens.space16,
                top = Theme.dimens.space16
            )
        ) {

            BpTitleWithContentSection(title = Resources.strings.ownerUsername) {
                Text(
                    ownerUsername,
                    modifier = Modifier.padding(
                        start = Theme.dimens.space16,
                        end = Theme.dimens.space16,
                        bottom = Theme.dimens.space16
                    ),
                    style = Theme.typography.body,
                    color = Theme.colors.contentPrimary
                )
            }
            BpTitleWithContentSection(title = Resources.strings.address) {
                Text(
                    address,
                    modifier = Modifier.padding(
                        start = Theme.dimens.space16,
                        end = Theme.dimens.space16,
                        bottom = Theme.dimens.space16
                    ),
                    style = Theme.typography.body,
                    color = Theme.colors.contentPrimary
                )
            }
            BpTitleWithContentSection(title = Resources.strings.rating) {
                BpRating(
                    rating,
                    selectedIcon = painterResource(Resources.images.filledStar),
                    halfSelectedIcon = painterResource(Resources.images.halfFilledStar),
                    modifier = Modifier.padding(
                        start = Theme.dimens.space16,
                        end = Theme.dimens.space16,
                        bottom = Theme.dimens.space16
                    ),
                    iconsSize = Theme.dimens.space16
                )
            }
            BpTitleWithContentSection(title = Resources.strings.priceLevel) {
                Text(
                    priceLevel,
                    modifier = Modifier.padding(
                        start = Theme.dimens.space16,
                        end = Theme.dimens.space16,
                        bottom = Theme.dimens.space16
                    ),
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.success
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RestaurantUpdateInformationCard(
        state: RestaurantInformationUiState,
        listener: RestaurantInformationInteractionListener
    ) {
        BpCard(
            modifier = Modifier.fillMaxWidth().padding(Theme.dimens.space16)
        ) {
            BpTextField(
                label = Resources.strings.restaurantName,
                text = state.restaurant.restaurantName,
                onValueChange = { listener.onRestaurantNameChange(it) },
                modifier = Modifier.padding(
                    horizontal = Theme.dimens.space16,
                    vertical = Theme.dimens.space16
                ),
                errorMessage = Resources.strings.restaurantNameErrorMessage,
                isError = state.restaurant.isRestaurantNameError
            )
            BpTextField(
                label = Resources.strings.phoneNumber,
                text = state.restaurant.phoneNumber,
                onValueChange = { listener.onPhoneNumberChange(it) },
                modifier = Modifier.padding(
                    start = Theme.dimens.space16,
                    end = Theme.dimens.space16,
                    bottom = Theme.dimens.space16
                ),
                isError = state.restaurant.isPhoneNumberError,
                errorMessage = Resources.strings.phoneNumberErrorMessage
            )
            Row(modifier = Modifier.padding(bottom = Theme.dimens.space16)) {
                BpTextField(
                    label = Resources.strings.workingHours,
                    text = state.restaurant.openingTime,
                    onValueChange = { listener.onOpeningTimeChange(it) },
                    modifier = Modifier.padding(
                        start = Theme.dimens.space16,
                        end = Theme.dimens.space4
                    ).weight(1f),
                    isError = state.restaurant.isOpeningTimeError,
                    errorMessage = Resources.strings.openingTimeErrorMessage
                )
                BpTextField(
                    label = "",
                    text = state.restaurant.closingTime,
                    onValueChange = { listener.onClosingTimeChange(it) },
                    modifier = Modifier.padding(
                        start = Theme.dimens.space4,
                        end = Theme.dimens.space16
                    ).weight(1f),
                    isError = state.restaurant.isClosingTimeError,
                    errorMessage = Resources.strings.closingTimeErrorMessage
                )
            }
            BpTextField(
                label = Resources.strings.description,
                text = state.restaurant.description,
                onValueChange = { listener.onDescriptionChanged(it) },
                modifier = Modifier.padding(
                    start = Theme.dimens.space16,
                    end = Theme.dimens.space16,
                    bottom = Theme.dimens.space24
                ),
                errorMessage = Resources.strings.descriptionErrorMessage,
                isError = state.restaurant.isDescriptionLengthError,
                singleLine = false,
                textFieldModifier = Modifier.fillMaxWidth().height(104.dp)
            )
            BpButton(
                title = Resources.strings.save,
                onClick = { listener.onClickSave() },
                enabled = state.restaurant.isSaveButtonEnabled,
                modifier = Modifier.fillMaxWidth().padding(
                    start = Theme.dimens.space16,
                    end = Theme.dimens.space16,
                    bottom = Theme.dimens.space16
                )
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun LogoutCard(
        onClickLogout: () -> Unit
    ) {
        BpCard(modifier = Modifier.fillMaxWidth().padding(
            start = Theme.dimens.space16,
            end = Theme.dimens.space16,
            bottom = Theme.dimens.space16
        ).clickable { onClickLogout() }) {
            Row {
                Icon(
                    painter = painterResource(Resources.images.logout), modifier = Modifier
                        .padding(
                            start = Theme.dimens.space16,
                            top = Theme.dimens.space16,
                            bottom = Theme.dimens.space16,
                            end = Theme.dimens.space8
                        ),
                    contentDescription = Resources.strings.logout,
                    tint = Theme.colors.primary
                )
                Text(
                    Resources.strings.logout,
                    style = Theme.typography.title.copy(color = Theme.colors.primary),
                    modifier = Modifier.padding(vertical = Theme.dimens.space16)
                )
            }
        }
    }
}