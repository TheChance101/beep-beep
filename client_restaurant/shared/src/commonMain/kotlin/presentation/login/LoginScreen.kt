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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
            is LoginScreenUIEffect.LoginEffect -> navigator.push(RestaurantSelectionScreen())
            LoginScreenUIEffect.LoginUIFailed -> {}
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

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Resources.images.backgroundPattern),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Card(
                colors = CardDefaults.cardColors(Theme.colors.background),
                shape = RoundedCornerShape(Theme.radius.medium),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        modifier = Modifier.height(44.dp).width(97.dp),
                        tint = Theme.colors.primary,
                        painter = painterResource(Resources.images.bpLogo),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(top = 32.dp),
                        text = "Welcome To Restaurant App",
                        color = Theme.colors.contentPrimary,
                        style = Theme.typography.titleLarge,
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp, top = 4.dp),
                        text = "Login to access all the features",
                        color = Theme.colors.contentTertiary,
                        style = Theme.typography.title,
                    )
                    BpTextField(
                        text = state.userName,
                        onValueChange = listener::onUserNameChanged,
                        label = Resources.strings.username,
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.usernameErrorMsg,
                        isError = state.isUsernameError,
                    )
                    BpTextField(
                        text = state.password,
                        onValueChange = listener::onPasswordChanged,
                        label = Resources.strings.password,
                        keyboardType = KeyboardType.Password,
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.passwordErrorMsg,
                        isError = state.isPasswordError

                    )
                    BpCheckBox(
                        label = Resources.strings.keepMeLoggedIn,
                        isChecked = state.keepLoggedIn,
                        onCheck = { listener.onKeepLoggedInClicked() },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
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
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    )
                }
            }

        }

    }
}

