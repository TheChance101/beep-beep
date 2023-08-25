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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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
            else -> {}
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


