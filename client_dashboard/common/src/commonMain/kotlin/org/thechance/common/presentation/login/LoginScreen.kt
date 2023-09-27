import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.delay
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.BpLogo
import org.thechance.common.presentation.composables.SnackBar
import org.thechance.common.presentation.login.LoginInteractionListener
import org.thechance.common.presentation.login.LoginScreenModel
import org.thechance.common.presentation.login.LoginUIEffect
import org.thechance.common.presentation.login.LoginUIState
import org.thechance.common.presentation.main.MainContainer
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms

class LoginScreen :
    BaseScreen<LoginScreenModel, LoginUIEffect, LoginUIState, LoginInteractionListener>() {

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }

    override fun onEffect(effect: LoginUIEffect, navigator: Navigator) {
        when (effect) {
            is LoginUIEffect.LoginSuccess -> {
                navigator.replaceAll(MainContainer)
            }

            is LoginUIEffect.LoginFailed -> {
                println(effect.errorMessage)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun OnRender(state: LoginUIState, listener: LoginInteractionListener) {
        Row(
                Modifier.background(Theme.colors.surface).fillMaxSize()
                    .padding(
                            top = 40.kms,
                            start = 40.kms,
                            bottom = 40.kms
                    ),
                horizontalArrangement = Arrangement.Center
        ) {
            Box(Modifier.weight(1f)) {
                Image(
                        painter = painterResource(Resources.Drawable.login),
                        contentDescription = null,
                        alignment = Alignment.CenterStart,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                            .border(
                                    BorderStroke(width = 1.kms, color = Theme.colors.divider),
                                    shape = RoundedCornerShape(Theme.radius.large)
                            )
                            .clip(RoundedCornerShape(Theme.radius.large))
                )
                BpLogo(
                        expanded = true,
                        modifier = Modifier.align(Alignment.TopStart).padding(32.kms)
                )
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {

                Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom,
                ) {
                    AnimatedVisibility( state.isSnackBarVisible,
                            modifier = Modifier.animateContentSize(),
                            enter = fadeIn(),
                            exit = fadeOut()
                    ) {
                        SnackBar(onDismiss = listener::onSnackBarDismiss, backgroundColor = Theme.colors.hover) {
                            Image(
                                    painter = painterResource(Resources.Drawable.infoIcon),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = Theme.colors.primary),
                                    modifier = Modifier.padding(16.kms)
                            )
                                Text(
                                        text = state.snackBarTitle?:Resources.Strings.noInternet,
                                        style = Theme.typography.titleMedium,
                                        color = Theme.colors.primary,
                                )
                        }
                    }

                }
                Column(
                        Modifier.fillMaxHeight().width(450.kms),
                        verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                            Resources.Strings.login,
                            style = Theme.typography.headlineLarge,
                            color = Theme.colors.contentPrimary
                    )
                    Text(
                            Resources.Strings.loginTitle,
                            style = Theme.typography.titleMedium,
                            color = Theme.colors.contentTertiary,
                            modifier = Modifier.padding(top = 8.kms)
                    )
                    BpTextField(
                            onValueChange = { listener.onUsernameChange(it) },
                            text = state.username,
                            label = Resources.Strings.loginUsername,
                            modifier = Modifier.padding(top = 40.kms),
                            errorMessage = state.isUserError?.errorMessage ?: "",
                            isError = state.isUserError?.isError ?: false,
                            hint = ""
                    )
                    BpTextField(
                            onValueChange = { listener.onPasswordChange(it) },
                            text = state.password,
                            label = Resources.Strings.loginPassword,
                            keyboardType = KeyboardType.Password,
                            modifier = Modifier.padding(top = 16.kms),
                            errorMessage = state.isPasswordError?.errorMessage ?: "",
                            isError = state.isPasswordError?.isError ?: false,
                            hint = ""
                    )
                    BpButton(
                            title = Resources.Strings.loginButton,
                            onClick = { listener.onLoginClicked() },
                            modifier = Modifier.padding(top = 24.kms).fillMaxWidth(),
                            enabled = state.isAbleToLogin
                    )
                }
            }
        }

        LaunchedEffect(state.isSnackBarVisible) {
            delay(1500)
            listener.onSnackBarDismiss()
        }
    }
}