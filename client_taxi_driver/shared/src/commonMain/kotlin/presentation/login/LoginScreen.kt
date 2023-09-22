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
import androidx.compose.ui.graphics.painter.Painter
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
import presentation.resources.Resources

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
            is LoginScreenUIEffect.LoginEffect -> navigator.replace(MainScreen())
            LoginScreenUIEffect.LoginUIFailed -> {/* TODO: add failed login effect*/
            }
        }
    }

    @Composable
    override fun onRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {

        Column(modifier = Modifier.fillMaxSize()) {
            CustomBottomSheet(
                sheetContent = {
                    if (state.bottomSheetUiState.showPermissionSheet) {
                        PermissionBottomSheetContent(
                            listener = listener,
                            state = state.bottomSheetUiState
                        )
                    } else WrongPermissionBottomSheet(listener)

                },
                sheetBackgroundColor = Theme.colors.background,
                onBackGroundClicked = listener::onDismissSheet,
                sheetState = state.bottomSheetUiState.sheetState,
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
            painter = painterResource(Resources.images.backgroundPattern),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        HeadFirstCard(
            textHeader = Resources.strings.welcomeToBeepBeepApp,
            textSubHeader = Resources.strings.loginToAccessAllTheFeatures,
        ) {
            BpTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                text = state.username,
                onValueChange = listener::onUsernameChanged,
                label = Resources.strings.username,
                keyboardType = KeyboardType.Text,
                errorMessage = state.usernameErrorMsg,
                isError = state.usernameErrorMsg.isNotEmpty(),
            )
            BpTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                text = state.password,
                onValueChange = listener::onPasswordChanged,
                label = Resources.strings.password,
                keyboardType = KeyboardType.Password,
                errorMessage = state.passwordErrorMsg,
                isError = state.passwordErrorMsg.isNotEmpty()

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
                        userName = state.username,
                        password = state.password,
                        isKeepMeLoggedInChecked = state.keepLoggedIn
                    )
                },
            )
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun HeadFirstCard(
    textHeader: String,
    textSubHeader: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp)
            .clip(shape = RoundedCornerShape(Theme.radius.medium))
            .background(Theme.colors.surface)
            .padding(16.dp)
    ) {
        CardHeader(
            textHeader = textHeader,
            textSubHeader = textSubHeader,
            logo = painterResource(Resources.images.beepBeepLogo)
        )
        content()
    }
}


@Composable
private fun CardHeader(
    textHeader: String,
    textSubHeader: String,
    logo: Painter,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(32.dp)) {
        Icon(
            modifier = Modifier.size(width = 70.dp, height = 32.dp),
            painter = logo,
            contentDescription = null,
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
    listener: LoginScreenBottomSheetInteractionListener,
    modifier: Modifier = Modifier,
    state: LoginScreenBottomSheetUiState
) {

    Column(
        modifier = modifier.wrapContentHeight()
            .padding(vertical = Theme.dimens.space24).padding(horizontal = Theme.dimens.space16)
    ) {
        Text(
            text = Resources.strings.askForPermission,
            color = Theme.colors.contentPrimary,
            style = Theme.typography.headlineLarge,
        )
        BpTextField(
            text = state.driverFullName,
            onValueChange = listener::onDriverFullNameChanged,
            label = Resources.strings.fullName,
            keyboardType = KeyboardType.Text,
            errorMessage = state.driverFullNameErrorMsg,
            isError = state.driverFullNameErrorMsg.isNotEmpty(),
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpTextField(
            text = state.driverEmail,
            onValueChange = listener::onOwnerEmailChanged,
            label = Resources.strings.userEmail,
            keyboardType = KeyboardType.Text,
            errorMessage = state.driverEmailErrorMsg,
            isError = state.driverEmailErrorMsg.isNotEmpty(),
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpExpandableTextField(
            text = state.description,
            onValueChange = listener::onDescriptionChanged,
            label = Resources.strings.whyBeepBeep,
            hint = Resources.strings.describeWhyYouWantToJoinUs,
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
            title = Resources.strings.submit,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
        BpTransparentButton(
            onClick = listener::onDismissSheet,
            title = Resources.strings.cancel,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16),
        )
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WrongPermissionBottomSheet(
    listener: LoginScreenBottomSheetInteractionListener,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentHeight()
            .padding(bottom = Theme.dimens.space24, top = Theme.dimens.space32)
            .padding(horizontal = Theme.dimens.space16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Resources.images.errorIcon),
            tint = Theme.colors.primary,
            contentDescription = null,
            modifier = Modifier.padding(bottom = Theme.dimens.space24),
        )

        Text(
            text = Resources.strings.wrongPermission,
            modifier = Modifier.padding(bottom = Theme.dimens.space16),
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary
        )
        Text(
            text = Resources.strings.wrongPermissionSheetDescription,
            modifier = Modifier.padding(bottom = Theme.dimens.space24),
            style = Theme.typography.body,
            textAlign = TextAlign.Center,
            color = Theme.colors.contentSecondary
        )

        BpButton(
            onClick = listener::onRequestPermissionClicked,
            title = Resources.strings.requestAPermission,
            modifier = Modifier.fillMaxWidth().padding(bottom = Theme.dimens.space16),
        )
        BpTransparentButton(
            onClick = listener::onDismissSheet,
            title = Resources.strings.close,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
