package presentation.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.LoginRequiredPlaceholder
import presentation.composable.modifier.noRippleEffect
import resources.Resources
import util.getStatusBarPadding
import util.root

class ProfileScreen :
    BaseScreen<ProfileScreenModel, ProfileUIState, ProfileUIEffect, ProfileInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: ProfileUIEffect, navigator: Navigator) {
        when (effect) {
            ProfileUIEffect.NavigateToLoginScreen -> navigator.root?.push(LoginScreen())
        }
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun onRender(state: ProfileUIState, listener: ProfileInteractionListener) {
        LoginRequiredPlaceholder(
            placeHolder = painterResource(Resources.images.requireLoginToShowProfilePlaceholder),
            message = Resources.strings.profileLoginMessage,
            onClickLogin = listener::onClickLogin
        )
        AnimatedVisibility(state.isLoggedIn, enter = fadeIn(), exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(getStatusBarPadding())
                    .background(Theme.colors.background)
                    .verticalScroll(rememberScrollState()),
            ) {
                WhiteCard {
                    Title(Resources.strings.wallet)
                    SubTitle(
                        "${state.user.currency} ${state.user.walletBalance}",
                        Theme.colors.primary
                    )
                    Title(Resources.strings.username)
                    SubTitle("@${state.user.username}")
                    Title(Resources.strings.address)
                    SubTitle(state.user.address)
                    Title(Resources.strings.email)
                    SubTitle(state.user.email)
                }
                WhiteCard {
                    BpTextField(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        text = state.fullName,
                        onValueChange = listener::onFullNameChanged,
                        label = Resources.strings.fullName,
                        keyboardType = KeyboardType.Text,
                        errorMessage = if (state.isFullNameError) Resources.strings.invalidFullName else "",
                        isError = state.isFullNameError,
                    )
                    BpTextField(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        text = state.phoneNumber,
                        onValueChange = listener::onPhoneNumberChanged,
                        label = Resources.strings.mobileNumber,
                        keyboardType = KeyboardType.Text,
                        errorMessage = if (state.isPhoneNumberError) Resources.strings.invalidPhoneNumber else "",
                        isError = state.isPhoneNumberError,
                    )
                    BpButton(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        title = Resources.strings.save,
                        enabled = state.isButtonEnabled,
                        isLoading = state.isLoading,
                        onClick = listener::onSaveProfileInfo,
                    )
                }
                WhiteCard {
                    Row(
                        modifier = Modifier.noRippleEffect(listener::onLogout),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            painter = painterResource(Resources.images.logout),
                            contentDescription = Resources.strings.logout,
                            tint = Theme.colors.primary,
                        )
                        Text(
                            text = Resources.strings.logout,
                            style = Theme.typography.title,
                            color = Theme.colors.primary,
                        )
                    }
                }
            }
        }

        AnimatedVisibility(state.isLoading, enter = fadeIn(), exit = fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Theme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = Theme.colors.primary
                )
            }
        }
    }


    @Composable
    private fun WhiteCard(content: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
                .clip(shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.surface)
                .padding(16.dp)
        ) {
            content()
        }
    }

    @Composable
    private fun Title(text: String) {
        Text(
            text = text,
            style = Theme.typography.caption,
            color = Theme.colors.contentTertiary,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
    }

    @Composable
    private fun SubTitle(text: String, color: Color = Theme.colors.contentPrimary) {
        Text(
            text = text,
            color = color,
            style = Theme.typography.body,
        )
    }
}
