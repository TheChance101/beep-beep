package presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.composable.CustomBottomSheet
import presentation.composable.ModalBottomSheetState
import presentation.login.composable.PermissionBottomSheetContent
import presentation.login.composable.WrongPermissionBottomSheet
import presentation.restaurantSelection.RestaurantSelectionScreen

class LoginScreen :
    BaseScreen<LoginScreenModel, LoginScreenUIState, LoginScreenUIEffect, LoginScreenInteractionListener>() {


    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { LoginScreenModel() }
        initScreen(screenModel)
    }

    override fun onEffect(
        effect: LoginScreenUIEffect,
        navigator: Navigator,
    ) {
        when (effect) {
            is LoginScreenUIEffect.Login -> navigator.push(RestaurantSelectionScreen(ownerId = ""))
        }
    }

    @Composable
    override fun onRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {

        var showPermissionSheet by remember { mutableStateOf(false) }
        val sheetState = remember { ModalBottomSheetState() }

        Column(modifier = Modifier.fillMaxSize()) {

            LaunchedEffect(sheetState.isVisible) {
                if (!sheetState.isVisible) {
                    showPermissionSheet = false
                }
            }

            CustomBottomSheet(
                sheetContent = {
                    if (showPermissionSheet) {
                        PermissionBottomSheetContent(
                            onCancelClick = {
                                showPermissionSheet = false
                                sheetState.dismiss()
                            },
                            onSubmit = {
                                showPermissionSheet = false
                                listener.onClickSubmit()
                                sheetState.dismiss()
                            },
                            listener = listener,
                            state = state
                        )
                    } else {
                        WrongPermissionBottomSheet(
                            onRequestPermissionClick = {
                                showPermissionSheet = true
                                listener.onClickSubmit()
                            },
                            onCancelClick = { sheetState.dismiss() },
                        )
                    }
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
                onClick = {
                    if (state.hasPermission) {
                        sheetState.show()
                    } else {
                        listener.onClickLogin()
                    }
                },
                title = "Login",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }


}
