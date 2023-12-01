package presentation.information

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BPSnackBar
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpRoundedImage
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpAppBar
import presentation.composable.BpCard
import presentation.composable.BpRating
import presentation.composable.BpTitleWithContentSection
import presentation.composable.modifier.noRippleEffect
import presentation.login.LoginScreen
import resources.Resources
import util.ImagePicker
import util.ImagePickerFactory
import util.getNavigationBarPadding
import util.getPlatformContext
import util.rememberBitmapFromBytes

class RestaurantInformationScreen(private val id: String) : BaseScreen<
        RestaurantInformationScreenModel,
        RestaurantInformationUiState,
        RestaurantInformationUiEffect,
        RestaurantInformationInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parametersOf(id) })
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: RestaurantInformationUiState,
        listener: RestaurantInformationInteractionListener,
    ) {

        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background)
                .padding(bottom = getNavigationBarPadding().calculateBottomPadding()),
            contentAlignment = Alignment.Center
        ) {
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
                    RestaurantInfoCard(
                        onImagePicked = listener::onImagePicked,
                        ownerUsername = state.restaurant.ownerUsername,
                        address = state.restaurant.address,
                        rating = state.restaurant.rating,
                        priceLevel = state.restaurant.priceLevel,
                        image = state.restaurant.image,
                        imageUrl = state.restaurant.imageUrl,
                        imagePicker = ImagePickerFactory(context = getPlatformContext()).createPicker()
                    )

                    RestaurantUpdateInformationCard(state, listener)

                    LogoutCard(listener::onClickLogout)
                }
            }
            BPSnackBar(
                icon = painterResource(Resources.images.bpIcon),
                iconBackgroundColor = Theme.colors.warningContainer,
                iconTint = Theme.colors.warning,
                isVisible = state.showSnackBar,
                modifier = Modifier.padding(bottom = getNavigationBarPadding().calculateBottomPadding())
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = if (state.snackBarState) Resources.strings.success else Resources.strings.error,
                    style = Theme.typography.body.copy(color = Theme.colors.contentPrimary),
                )
            }
        }
    }

    override fun onEffect(effect: RestaurantInformationUiEffect, navigator: Navigator) {
        when (effect) {
            is RestaurantInformationUiEffect.LogoutSuccess -> navigator.replaceAll(LoginScreen())
            is RestaurantInformationUiEffect.NavigateUp -> navigator.pop()
            RestaurantInformationUiEffect.ShowNoInternetError -> {}
            RestaurantInformationUiEffect.ShowUnknownError -> {}
            RestaurantInformationUiEffect.UpdateInformationSuccess -> {}

            else -> {}
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun RestaurantInfoCard(
        onImagePicked: (ByteArray) -> Unit,
        ownerUsername: String,
        address: String,
        rating: Double,
        priceLevel: String,
        image: ByteArray?,
        imageUrl: String,
        imagePicker: ImagePicker,
    ) {
        imagePicker.registerPicker { onImagePicked(it) }
        BpCard(
            modifier = Modifier.fillMaxWidth().padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    bottom = 16.dp,
                    top = 16.dp
                ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (imageUrl.isEmpty() || image != null) {
                    BpRoundedImage(
                        modifier = Modifier.sizeIn(minHeight = 120.dp).fillMaxWidth(),
                        bitmap = rememberBitmapFromBytes(image),
                        placeholder = painterResource(Resources.images.galleryAdd),
                        onClick = { imagePicker.pickImage() }
                    )
                } else {
                    BpRoundedImage(
                        modifier = Modifier.sizeIn(minHeight = 120.dp).fillMaxWidth(),
                        painter = rememberAsyncImagePainter(url = imageUrl),
                        editPainter = painterResource(Resources.images.edit),
                        onClick = { imagePicker.pickImage() }
                    )
                }
            }
            BpTitleWithContentSection(title = Resources.strings.ownerUsername) {
                Text(
                    ownerUsername,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                    style = Theme.typography.body,
                    color = Theme.colors.contentPrimary
                )
            }
            BpTitleWithContentSection(title = Resources.strings.address) {
                Text(
                    address,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
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
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                    iconsSize = 16.dp
                )
            }
            BpTitleWithContentSection(title = Resources.strings.priceLevel) {
                Text(
                    priceLevel,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
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
        listener: RestaurantInformationInteractionListener,
    ) {
        BpCard(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            BpTextField(
                label = Resources.strings.restaurantName,
                text = state.restaurant.restaurantName,
                onValueChange = { listener.onRestaurantNameChange(it) },
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                errorMessage = Resources.strings.restaurantNameErrorMessage,
                isError = state.restaurant.isRestaurantNameError
            )
            BpTextField(
                label = Resources.strings.phoneNumber,
                text = state.restaurant.phoneNumber,
                onValueChange = { listener.onPhoneNumberChange(it) },
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                isError = state.restaurant.isPhoneNumberError,
                errorMessage = Resources.strings.phoneNumberErrorMessage
            )
            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                BpTextField(
                    label = Resources.strings.workingHours,
                    text = state.restaurant.openingTime,
                    onValueChange = { listener.onOpeningTimeChange(it) },
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 4.dp
                    ).weight(1f),
                    isError = state.restaurant.isOpeningTimeError,
                    errorMessage = Resources.strings.openingTimeErrorMessage
                )
                BpTextField(
                    label = "",
                    text = state.restaurant.closingTime,
                    onValueChange = { listener.onClosingTimeChange(it) },
                    modifier = Modifier.padding(
                        start = 4.dp,
                        end = 16.dp
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
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                ),
                errorMessage = Resources.strings.descriptionErrorMessage,
                isError = state.restaurant.isDescriptionLengthError,
                singleLine = false,
                textFieldModifier = Modifier.fillMaxWidth().height(104.dp)
            )
            BpButton(
                title = Resources.strings.save,
                onClick = { listener.onClickSave() },
                enabled = state.restaurant.isSaveButtonEnabled && state.restaurant.validationState,
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth().padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun LogoutCard(
        onClickLogout: () -> Unit,
    ) {
        BpCard(modifier = Modifier.fillMaxWidth().padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ).noRippleEffect { onClickLogout() }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(Resources.images.logout), modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 16.dp,
                            bottom = 16.dp,
                            end = 8.dp
                        ),
                    contentDescription = Resources.strings.logout,
                    tint = Theme.colors.primary
                )
                Text(
                    Resources.strings.logout,
                    style = Theme.typography.title.copy(color = Theme.colors.primary),
                )
            }
        }
    }
}
