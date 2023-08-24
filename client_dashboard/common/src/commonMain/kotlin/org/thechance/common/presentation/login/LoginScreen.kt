package org.thechance.common.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.BpLogo
import org.thechance.common.presentation.main.MainContainer


class LoginScreen : BaseScreen<LoginScreenScreenModel, LoginUIEffect, LoginUIState, LoginScreenInteractionListener>() {

    override fun onEffect(effect: LoginUIEffect, navigator: Navigator) {
        when (effect) {
            LoginUIEffect.LoginUISuccess -> {
                navigator push MainContainer
            }

            LoginUIEffect.LoginUIFailed -> {}
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun OnRender(state: LoginUIState, listener: LoginScreenInteractionListener) {
        Row(
            Modifier.background(Theme.colors.surface).fillMaxSize()
                .padding(
                    top = Theme.dimens.space40,
                    start = Theme.dimens.space40,
                    bottom = Theme.dimens.space40
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(Modifier.weight(1f)) {
                Image(
                    painter = painterResource(
                        if (isSystemInDarkTheme()) "login_image_dark.png" else "login_image_light.png"
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
                    modifier = Modifier.align(Alignment.TopStart).padding(Theme.dimens.space32)
                )
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(
                    Modifier.fillMaxHeight().width(350.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "Login",
                        style = Theme.typography.headlineLarge,
                        color = Theme.colors.contentPrimary
                    )
                    Text(
                        "Use admin account to login",
                        style = Theme.typography.titleMedium,
                        color = Theme.colors.contentTertiary,
                        modifier = Modifier.padding(top = Theme.dimens.space8)
                    )
                    BpTextField(
                        onValueChange = { listener.onUsernameChange(it) },
                        text = state.username,
                        label = "Username",
                        errorMessage = state.usernameError,
                        isError = state.isUsernameError,
                        modifier = Modifier.padding(top = Theme.dimens.space40),
                        hint = ""
                    )
                    BpTextField(
                        onValueChange = { listener.onPasswordChange(it) },
                        text = state.password,
                        label = "Password",
                        errorMessage = state.passwordError,
                        isError = state.isPasswordError,
                        keyboardType = KeyboardType.Password,
                        modifier = Modifier.padding(top = Theme.dimens.space16),
                        hint = ""
                    )
                    BpCheckBox(
                        label = "Keep me logged in",
                        isChecked = state.keepLoggedIn,
                        onCheck = { listener.onKeepLoggedInClicked() },
                        modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16)
                    )
                    BpButton(
                        title = "Login",
                        onClick = { listener.onLoginClicked() },
                        modifier = Modifier.padding(top = Theme.dimens.space24).fillMaxWidth()
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