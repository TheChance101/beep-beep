package presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.CustomBottomSheet
import presentation.main.MainScreen

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
            is LoginScreenUIEffect.LoginEffect -> navigator.push(MainScreen())
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
            painter = painterResource("background_pattern.png"),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        HeadFirstCard(
            textHeader = "Welcome To Beep Beep App",
            textSubHeader = "Login to access all the features",
        ) {
            BpTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                text = state.userName,
                onValueChange = listener::onUserNameChanged,
                label = "Username",
                keyboardType = KeyboardType.Text,
                errorMessage = state.usernameErrorMsg,
                isError = state.isUsernameError,
            )
            BpTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                text = state.password,
                onValueChange = listener::onPasswordChanged,
                label = "Password",
                keyboardType = KeyboardType.Password,
                errorMessage = state.passwordErrorMsg,
                isError = state.isPasswordError

            )
            BpCheckBox(
                modifier = Modifier.padding(top = 16.dp),
                label = "Keep me logged in",
                isChecked = state.keepLoggedIn,
                size = 24,
                textStyle = Theme.typography.caption,
                textColor = Theme.colors.contentSecondary,
                onCheck = listener::onKeepLoggedInClicked
            )
            BpButton(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                title = "login",
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


@Composable
private fun HeadFirstCard(
    textHeader: String,
    textSubHeader: String,
    modifier: Modifier = Modifier,
    logo: String = "ic_beep_beep_logo.xml",
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp)
            .clip(shape = RoundedCornerShape(Theme.radius.medium))
            .background(Theme.colors.surface)
            .padding(16.dp)
    ) {
        CardHeader(textHeader = textHeader, textSubHeader = textSubHeader, logo = logo)
        content()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CardHeader(
    textHeader: String,
    textSubHeader: String,
    logo: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(32.dp)) {
        Icon(
            modifier = Modifier.size(width = 70.dp, height = 32.dp),
            painter = painterResource(logo),
            contentDescription = logo,
            tint = Theme.colors.primary
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = textHeader,
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary
            )
            Text(
                text = textSubHeader,
                style = Theme.typography.body,
                color = Theme.colors.contentTertiary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionBottomSheetContent(
    listener: LoginScreenInteractionListener,
    modifier: Modifier = Modifier,
    state: LoginScreenUIState
) {

    Column(
        modifier = modifier.wrapContentHeight()
            .padding(vertical = Theme.dimens.space24).padding(horizontal = Theme.dimens.space16)
    ) {
        Text(
            text = "Ask for permission",
            color = Theme.colors.contentPrimary,
            style = Theme.typography.headlineLarge,
        )
        BpTextField(
            text = state.driverFullName,
            onValueChange = listener::onDriverFullNameChanged,
            label = "Full name",
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpTextField(
            text = state.driverEmail,
            onValueChange = listener::onOwnerEmailChanged,
            label = "User email",
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpExpandableTextField(
            text = state.description,
            onValueChange = listener::onDescriptionChanged,
            label = "Why beep beep?",
            hint = "Describe why you want to join us",
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpButton(
            onClick = {
                listener.onSubmitClicked(
                    state.driverFullName,
                    state.driverEmail,
                    state.description
                )
            },
            title = "Submit",
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpTransparentButton(
            onClick = listener::onCancelClicked,
            title = "cancel",
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WrongPermissionBottomSheet(
    listener: LoginScreenInteractionListener,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentHeight()
            .padding(bottom = Theme.dimens.space24, top = Theme.dimens.space32)
            .padding(horizontal = Theme.dimens.space16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource("ic_error_icon.xml"),
            tint = Theme.colors.primary,
            contentDescription = null,
            modifier = Modifier.padding(bottom = Theme.dimens.space24),
        )

        Text(
            text = "Wrong permission",
            modifier = Modifier.padding(bottom = Theme.dimens.space16),
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary
        )
        Text(
            text = "Looks like  your account isn’t assigned as a restaurant owner, ask for permission?",
            modifier = Modifier.padding(bottom = Theme.dimens.space24),
            style = Theme.typography.body,
            textAlign = TextAlign.Center,
            color = Theme.colors.contentSecondary
        )

        BpButton(
            onClick = listener::onRequestPermissionClicked,
            title = "Request a permission",
            modifier = Modifier.fillMaxWidth().padding(bottom = Theme.dimens.space16),
        )
        BpTransparentButton(
            onClick = listener::onCancelClicked,
            title = "Close",
            modifier = Modifier.fillMaxWidth(),
        )
    }

}