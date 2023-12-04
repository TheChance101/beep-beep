package presentation.auth.signup.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.signup.registrationSubmit.RegistrationSubmitScreen
import presentation.base.BaseScreen
import presentation.composable.BpBrandBackgroundContainer
import presentation.composable.HeadFirstCard
import presentation.composable.modifier.noRippleEffect
import com.beepbeep.designSystem.ui.composable.BpToastMessage
import resources.Resources
import util.getNavigationBarPadding
import util.getStatusBarPadding

class RegistrationScreen :
    BaseScreen<RegistrationScreenModel, RegistrationUIState, RegistrationScreenEffect, RegistrationInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: RegistrationUIState,
        listener: RegistrationInteractionListener
    ) {
        BpBrandBackgroundContainer {
            Row(
                Modifier.padding(getStatusBarPadding()).height(56.dp).fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    Modifier.size(40.dp).clip(RoundedCornerShape(Theme.radius.medium))
                        .background(color = Theme.colors.surface)
                        .noRippleEffect { listener.onBackButtonClicked() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Resources.images.iconBack),
                        contentDescription = null,
                        tint = Theme.colors.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            HeadFirstCard(
                textHeader = Resources.strings.joinBpToday,
                textSubHeader = Resources.strings.createYourAccount
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
                    text = state.email,
                    onValueChange = listener::onEmailChanged,
                    label = Resources.strings.email,
                    errorMessage = if (state.isEmailError) Resources.strings.invalidEmail else "",
                    isError = state.isEmailError,
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
                BpButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    title = Resources.strings.next,
                    onClick = listener::onNextButtonClicked,
                    isLoading = state.isLoading,
                )
            }

            LaunchedEffect(state.showSnackbar) {
                if (state.showSnackbar) {
                    delay(2000)
                    listener.onDismissSnackBar()
                }
            }
            BpToastMessage(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                    .padding(bottom = getNavigationBarPadding().calculateBottomPadding()),
                state = state.showSnackbar,
                message = state.snackbarMessage,
                isError = state.snackbarMessage.isNotEmpty(),
                successIcon = painterResource(Resources.images.unread),
                warningIcon = painterResource(Resources.images.warningIcon)
            )
        }
    }

    override fun onEffect(effect: RegistrationScreenEffect, navigator: Navigator) {
        when (effect) {
            is RegistrationScreenEffect.NavigateBack -> navigator.pop()
            is RegistrationScreenEffect.NavigateToSubmitRegistrationScreen -> navigator.push(
                RegistrationSubmitScreen(
                    username = effect.username,
                    email = effect.email,
                    password = effect.password
                )
            )
        }
    }
}





