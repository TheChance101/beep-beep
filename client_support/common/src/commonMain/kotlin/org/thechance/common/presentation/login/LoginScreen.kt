package org.thechance.common.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.chat.ChatScreen
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
            LoginUIEffect.LoginSuccess -> {
                navigator.replaceAll(ChatScreen())
            }

            is LoginUIEffect.LoginFailed -> {

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
                Image(
                    modifier = Modifier.align(Alignment.TopStart).padding(32.kms),
                    painter = painterResource(Resources.Drawable.beepBeepLogoExpanded),
                    contentDescription = null,
                    alignment = Alignment.CenterStart,
                )
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
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
                        hint = ""
                    )
                    BpTextField(
                        onValueChange = { listener.onPasswordChange(it) },
                        text = state.password,
                        label = Resources.Strings.loginPassword,
                        keyboardType = KeyboardType.Password,
                        modifier = Modifier.padding(top = 16.kms),
                        hint = ""
                    )
                    BpCheckBox(
                        label = Resources.Strings.loginKeepMeLoggedIn,
                        isChecked = state.keepLoggedIn,
                        onCheck = { listener.onKeepLoggedInClicked() },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.kms)
                    )
                    BpButton(
                        title = Resources.Strings.login,
                        onClick = { listener.onLoginClicked() },
                        modifier = Modifier.padding(top = 24.kms).fillMaxWidth(),
                        enabled = true,
                        isLoading = state.isLoading
                    )
                }
            }
        }
    }
}