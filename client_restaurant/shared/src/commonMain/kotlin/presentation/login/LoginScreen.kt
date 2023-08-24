package presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.composables.CustomBottomSheet
import presentation.composables.ModalBottomSheetState
import presentation.main.MainScreen
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
            is LoginScreenUIEffect.Login -> navigator.push(MainScreen())
            is LoginScreenUIEffect.Permission -> navigator.push(MainScreen())
        }
    }

    @Composable
    override fun onRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {
        val sheetState = remember { ModalBottomSheetState() }
        Column(modifier = Modifier.fillMaxSize()) {

            CustomBottomSheet(
                sheetContent = {
                    PermissionBottomSheetContent(
                        state = state,
                        onCancelClick = { sheetState.dismiss() },
                        onSubmit = {
                            listener.onClickSubmit()
                            sheetState.dismiss()
                        },
                        listener = listener,
                    )
                },
                sheetBackgroundColor = Theme.colors.background,
                sheetState = sheetState,
            ) { LoginScreenContent(state, listener, sheetState) }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LoginScreenContent(
        state: LoginScreenUIState,
        listener: LoginScreenInteractionListener,
        sheetState: ModalBottomSheetState,
    ) {
        Column(
            Modifier.fillMaxSize().background(Theme.colors.secondary),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Ask for Permission",
                modifier = Modifier.padding(bottom = Theme.dimens.space8),
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )
            BpTextField(
                text = state.email,
                onValueChange = listener::onEmailChanged,
                label = "Username",
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
                errorMessage = state.error,
            )
            BpTextField(
                text = state.password,
                onValueChange = listener::onPasswordChanged,
                label = "Password",
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth(),
                errorMessage = state.error,
            )
            BpButton(
                onClick = { sheetState.show() },
                title = "Login",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun PermissionBottomSheetContent(
        state: LoginScreenUIState,
        listener: LoginScreenInteractionListener,
        onSubmit: () -> Unit,
        onCancelClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier.wrapContentHeight()
                .padding(top = Theme.dimens.space16).padding(horizontal = Theme.dimens.space16)
        ) {
            Text(
                "Ask for permission",
                style = Theme.typography.headlineLarge,
            )
            BpTextField(
                text = state.restaurantName,
                onValueChange = listener::onNameChanged,
                label = Resources.strings.name,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )
            BpTextField(
                text = state.ownerEmail,
                onValueChange = listener::onNameChanged,
                label = Resources.strings.name,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )
            BpExpandableTextField(
                text = state.description,
                onValueChange = listener::onDescriptionChanged,
                label = Resources.strings.description,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
            )
            BpButton(
                onClick = onSubmit,
                title = "Submit",
                modifier = Modifier.fillMaxWidth(),
            )
            BpButton(
                onClick = onCancelClick,
                title = "Cancel",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

}
