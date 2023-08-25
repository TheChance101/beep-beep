package presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composables.CustomBottomSheet
import presentation.composables.ModalBottomSheetState
import presentation.login.composable.PermissionBottomSheetContent
import presentation.login.composable.WrongPermissionBottomSheet
import presentation.main.MainScreen
import resources.Resources

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
            is LoginScreenUIEffect.Login -> navigator.push(MainScreen(""))
            is LoginScreenUIEffect.Permission -> navigator.push(MainScreen(""))
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

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
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
            // region header
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f)) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Resources.images.backgroundPattern),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Icon(
                    modifier = Modifier.height(44.dp).width(97.dp).align(Alignment.Center),
                    tint = Color.Red,
                    painter = painterResource(Resources.images.bpLogo),
                    contentDescription = null
                )
            }

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
