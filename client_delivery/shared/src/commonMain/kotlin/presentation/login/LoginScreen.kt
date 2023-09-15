package presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.login.composable.LoginScaffold
import presentation.login.composable.LogoHeaderCard
import presentation.login.composable.RequestPermissionBottomSheet
import presentation.login.composable.WrongPermissionBottomSheet
import presentation.main.MainScreen
import resources.Resources

class LoginScreen : BaseScreen<
        LoginScreenModel,
        LoginScreenUIState,
        LoginScreenUIEffect,
        LoginScreenInteractionListener>() {

    @Composable
    override fun onRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {
        Column(modifier = Modifier.fillMaxSize()) {
            LoginScaffold(
                sheetContent = {
                    if (state.showPermissionSheet) {
                        RequestPermissionBottomSheet(
                            listener = listener,
                            state = state
                        )
                    } else {
                        WrongPermissionBottomSheet(
                            listener
                        )
                    }
                },
                sheetBackgroundColor = Theme.colors.background,
                onBackGroundClicked = listener::onSheetBackgroundClicked,
                sheetState = state.sheetState,
            ) {
                LoginScreenContent(state, listener)
            }
        }
    }

    override fun onEffect(effect: LoginScreenUIEffect, navigator: Navigator) {
          when (effect) {
            is LoginScreenUIEffect.LoginEffect -> navigator.push(MainScreen())
            LoginScreenUIEffect.LoginUIFailed -> {}
        }
    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
private fun LoginScreenContent(
    state: LoginScreenUIState,
    listener: LoginScreenInteractionListener
) {

    Box(
        modifier = Modifier.fillMaxSize().background(Theme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Resources.images.backgroundPattern),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        LogoHeaderCard(
            textHeader = Resources.strings.loginWelcomeMessage,
            textSubHeader = Resources.strings.loginSubWelcomeMessage,
        ) {
            BpTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                text = state.userName,
                onValueChange = listener::onUserNameChanged,
                label = Resources.strings.usernameLabel,
                keyboardType = KeyboardType.Text,
                errorMessage = state.usernameErrorMsg,
                isError = state.isUsernameError,
            )
            BpTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                text = state.password,
                onValueChange = listener::onPasswordChanged,
                label = Resources.strings.passwordLabel,
                keyboardType = KeyboardType.Password,
                errorMessage = state.passwordErrorMsg,
                isError = state.isPasswordError

            )
            BpCheckBox(
                modifier = Modifier.padding(top = 16.dp),
                label = Resources.strings.keepMeLoggedIn,
                isChecked = state.keepLoggedIn,
                size = 24,
                textStyle = Theme.typography.caption,
                textColor = Theme.colors.contentSecondary,
                onCheck = listener::onKeepLoggedInClicked
            )
            BpButton(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                title = Resources.strings.login,
                onClick = {
                    listener.onClickLogin(
                        userName = state.userName,
                        password = state.password,
                        isKeepMeLoggedInChecked = state.keepLoggedIn
                    )
                },
            )
        }
    }
}