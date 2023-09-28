package presentation.auth.signup.registrationSubmit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BPSnackBar
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.BpBrandBackgroundContainer
import presentation.composable.HeadFirstCard
import presentation.composable.modifier.noRippleEffect
import resources.Resources

class RegistrationSubmitScreen(
    private val username: String,
    private val email: String,
    private val password: String
) : BaseScreen<RegistrationSubmitScreenModel, RegistrationSubmitUIState, RegistrationSubmitScreenEffect, RegistrationSubmitInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel { parametersOf(username, email, password) })
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: RegistrationSubmitUIState,
        listener: RegistrationSubmitInteractionListener
    ) {
        BpBrandBackgroundContainer {
            Row(
                Modifier.height(56.dp).fillMaxWidth().align(Alignment.TopCenter)
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
                textHeader = Resources.strings.justOnMoreStep,
                textSubHeader = Resources.strings.completeYourRegistration
            ) {
                BpTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    text = state.fullName,
                    onValueChange = listener::onFullNameChanged,
                    label = Resources.strings.fullName,
                    errorMessage = if (state.isFullNameError) Resources.strings.invalidFullName else "",
                    isError = state.isFullNameError,
                )
                BpTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    text = state.phone,
                    onValueChange = listener::onPhoneChanged,
                    label = Resources.strings.mobileNumber,
                    errorMessage = if (state.isPhoneError) Resources.strings.invalidPhoneNumber else "",
                    isError = state.isPhoneError,
                )
                BpTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    text = state.address,
                    onValueChange = listener::onAddressChanged,
                    label = Resources.strings.yourAddress,
                    errorMessage = if (state.isAddressError) Resources.strings.invalidAddress else "",
                    isError = state.isAddressError,
                )
                BpButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    title = Resources.strings.signUp,
                    onClick = listener::onSignUpButtonClicked,
                    enabled = !state.isLoading
                )
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

    override fun onEffect(effect: RegistrationSubmitScreenEffect, navigator: Navigator) {
        when (effect) {
            RegistrationSubmitScreenEffect.NavigateBack -> navigator.pop()
            RegistrationSubmitScreenEffect.NavigateToLoginScreen -> navigator.replaceAll(LoginScreen())
        }
    }
}





