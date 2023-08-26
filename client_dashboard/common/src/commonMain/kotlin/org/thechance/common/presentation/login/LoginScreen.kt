package org.thechance.common.presentation.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.LocalDimensions
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.BpLogo
import org.thechance.common.presentation.main.MainContainer
import org.thechance.common.presentation.resources.Resources


class LoginScreen : BaseScreen<LoginScreenScreenModel, LoginUIEffect, LoginUIState, LoginScreenInteractionListener>() {

    override fun onEffect(effect: LoginUIEffect, navigator: Navigator) {
        when (effect) {
            LoginUIEffect.LoginSuccess -> {
                navigator push MainContainer
            }

            is LoginUIEffect.LoginFailed -> {

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun OnRender(state: LoginUIState, listener: LoginScreenInteractionListener) {
        Row(
            Modifier.background(Theme.colors.surface).fillMaxSize()
                .padding(
                    top = LocalDimensions.current.space40,
                    start = LocalDimensions.current.space40,
                    bottom = LocalDimensions.current.space40
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(Modifier.weight(1f)) {
                Image(
                    painter = painterResource(
                        if (isSystemInDarkTheme()) Resources.Strings.loginImageDark else Resources.Strings.loginImageLight
                    ),
                    contentDescription = null,
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .border(
                            BorderStroke(width = 1.dp, color = Theme.colors.divider),
                            shape = RoundedCornerShape(Theme.radius.large)
                        )
                        .clip(RoundedCornerShape(Theme.radius.large))
                )
                BpLogo(
                    expanded = true,
                    modifier = Modifier.align(Alignment.TopStart).padding(LocalDimensions.current.space32)
                )
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(
                    Modifier.fillMaxHeight().width(350.dp),
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
                        modifier = Modifier.padding(top = LocalDimensions.current.space8)
                    )
                    BpTextField(
                        onValueChange = { listener.onUsernameChange(it) },
                        text = state.username,
                        label = Resources.Strings.loginUsername,
                        errorMessage = state.usernameError,
                        isError = state.isUsernameError,
                        modifier = Modifier.padding(top = LocalDimensions.current.space40),
                        hint = ""
                    )
                    BpTextField(
                        onValueChange = { listener.onPasswordChange(it) },
                        text = state.password,
                        label = Resources.Strings.loginPassword,
                        errorMessage = state.passwordError,
                        isError = state.isPasswordError,
                        keyboardType = KeyboardType.Password,
                        modifier = Modifier.padding(top = LocalDimensions.current.space16),
                        hint = ""
                    )
                    BpCheckBox(
                        label = Resources.Strings.loginKeepMeLoggedIn,
                        isChecked = state.keepLoggedIn,
                        onCheck = { listener.onKeepLoggedInClicked() },
                        modifier = Modifier.fillMaxWidth().padding(top = LocalDimensions.current.space16)
                    )
                    BpButton(
                        title = Resources.Strings.loginButton,
                        onClick = { listener.onLoginClicked() },
                        modifier = Modifier.padding(top = LocalDimensions.current.space24).fillMaxWidth()
                    )
                }
            }
        }
    }

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }
}