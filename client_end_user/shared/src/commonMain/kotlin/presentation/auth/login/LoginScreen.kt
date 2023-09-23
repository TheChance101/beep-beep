package presentation.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BPSnackBar
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.signup.registration.RegistrationScreen
import presentation.base.BaseScreen
import presentation.composable.BpBrandBackgroundContainer
import presentation.composable.HeadFirstCard
import presentation.composable.SimpleTextButton
import presentation.main.MainContainer
import resources.Resources

class LoginScreen :
    BaseScreen<LoginScreenModel, LoginScreenUIState, LoginScreenUIEffect, LoginScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(
        effect: LoginScreenUIEffect,
        navigator: Navigator,
    ) {
        when (effect) {
            is LoginScreenUIEffect.NavigateToHome -> navigator.replaceAll(MainContainer)
            LoginScreenUIEffect.NavigateToSignup -> navigator.push(RegistrationScreen())
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {
        BpBrandBackgroundContainer {
            HeadFirstCard(
                textHeader = Resources.strings.loginWelcomeMessage,
                textSubHeader = Resources.strings.loginSubWelcomeMessage
            ) {
                BpTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    text = state.username,
                    onValueChange = listener::onUsernameChanged,
                    label = Resources.strings.username,
                    keyboardType = KeyboardType.Text,
                    errorMessage = if (state.isUsernameError) Resources.strings.invalidUsername else "",
                    isError = state.isUsernameError,
                )
                BpTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    text = state.password,
                    onValueChange = listener::onPasswordChanged,
                    label = Resources.strings.password,
                    keyboardType = KeyboardType.Password,
                    errorMessage = if (state.isPasswordError) Resources.strings.invalidPassword else "",
                    isError = state.isPasswordError,
                )
                BpCheckBox(
                    modifier = Modifier.padding(top = 16.dp),
                    label = Resources.strings.keepMeLoggedIn,
                    onCheck = listener::onKeepLoggedInChecked,
                    isChecked = state.keepLoggedIn,
                    size = 24,
                    textStyle = Theme.typography.caption,
                    textColor = Theme.colors.contentSecondary
                )
                BpButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    title = Resources.strings.login,
                    onClick = {
                        listener.onClickLogin(
                            username = state.username,
                            password = state.password,
                            keepLoggedIn = state.keepLoggedIn
                        )
                    },
                    enabled = !state.isLoading
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 32.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = Resources.strings.signUpDescription,
                        style = Theme.typography.caption,
                        color = Theme.colors.contentTertiary
                    )
                    SimpleTextButton(
                        modifier = Modifier.padding(vertical = 2.dp),
                        text = Resources.strings.signUpNow,
                        onClick = listener::onClickSignUp,
                        border = BorderStroke(width = 0.dp, color = Color.Transparent),
                    )
                }
            }

            AnimatedVisibility(
                visible = state.showSnackbar,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                BPSnackBar(icon = painterResource(Resources.images.icError)) {
                    Text(
                        text = state.snackbarMessage,
                        style = Theme.typography.body.copy(color = Theme.colors.contentPrimary),
                    )
                }
            }
        }
    }
}
