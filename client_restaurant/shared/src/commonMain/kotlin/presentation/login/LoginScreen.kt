package presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composables.CustomBottomSheet
import presentation.login.composable.PermissionBottomSheetContent
import presentation.login.composable.WrongPermissionBottomSheet
import presentation.restaurantSelection.RestaurantSelectionScreen
import resources.Resources

class LoginScreen :
    BaseScreen<LoginScreenModel, LoginScreenUIState, LoginScreenUIEffect, LoginScreenInteractionListener>() {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { LoginScreenModel() }
        initScreen(screenModel)
    }

    override fun onEffect(
        effect: LoginScreenUIEffect,
        navigator: Navigator,
    ) {
        when (effect) {
            is LoginScreenUIEffect.LoginEffect -> navigator.push(RestaurantSelectionScreen(ownerId = ""))
        }
    }

    @Composable
    override fun onRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {

        Column(modifier = Modifier.fillMaxSize()) {

            CustomBottomSheet(
                sheetContent = {
                    if (state.showPermissionSheet) {
                        PermissionBottomSheetContent(
                            listener = listener,
                            state = state
                        )
                    } else {
                        WrongPermissionBottomSheet(
                            listener = listener
                        )
                    }
                },
                sheetBackgroundColor = Theme.colors.background,
                sheetState = state.sheetState,
            ) { LoginScreenContent(state, listener) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
private fun LoginScreenContent(
    state: LoginScreenUIState,
    listener: LoginScreenInteractionListener
) {
    Column(
        Modifier.fillMaxSize().background(Theme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Resources.images.backgroundPattern),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Icon(
                modifier = Modifier.height(44.dp).width(97.dp).align(Alignment.Center),
                tint = Theme.colors.primary,
                painter = painterResource(Resources.images.bpLogo),
                contentDescription = null
            )
        }
        Column(
            Modifier.padding(horizontal = Theme.dimens.space16),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BpTextField(
                text = state.userName,
                onValueChange = listener::onUserNameChanged,
                label = Resources.strings.username,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
                errorMessage = state.error.toString(),
            )
            BpTextField(
                text = state.password,
                onValueChange = listener::onPasswordChanged,
                label = Resources.strings.password,
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth(),
                errorMessage = state.error.toString(),
            )
            BpCheckBox(
                label = Resources.strings.keepMeLoggedIn,
                isChecked = state.keepLoggedIn,
                onCheck = { listener.onKeepLoggedInClicked() },
                modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16)
                    .background(Theme.colors.background)
            )
            BpButton(
                onClick = {
                    listener.onClickLogin(
                        state.userName,
                        state.password,
                        state.keepLoggedIn
                    )
                },
                title = Resources.strings.login,
                modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
            )
        }
    }
}

