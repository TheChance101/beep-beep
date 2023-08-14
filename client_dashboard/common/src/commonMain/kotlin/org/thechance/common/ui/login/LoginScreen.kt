package org.thechance.common.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


object LoginScreen : Screen, KoinComponent {

    private val screenModel: LoginScreenModel by inject()

    @Composable
    override fun Content() {
        val navigate = LocalNavigator.currentOrThrow
        val state by screenModel.state.collectAsState()

        LoginContent(
            state = state,
            onClickLogin = { },
            onUserNameChanged = screenModel::onUsernameChange,
            onPasswordChanged = screenModel::onPasswordChange,
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent(
    state: LoginUiState,
    onClickLogin: () -> Unit,
    onUserNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    Row(
        Modifier.fillMaxSize().padding(top = 40.dp, start = 40.dp, bottom = 40.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(Modifier.weight(1f)) {
            Image(
                painter = painterResource("login_image.png"),
                contentDescription = null,
                alignment = Alignment.CenterStart,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .border(
                        BorderStroke(width = 1.dp, color = Color(0xFF1F0000).copy(.08f)),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
            )
            Image(
                painterResource("ic_beepbeep_logo_expanded.svg"),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopStart).padding(32.dp)
            )
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column(
                Modifier.fillMaxHeight().width(350.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Login",style = Theme.typography.headlineLarge, color = Theme.colors.contentPrimary)
                Text(
                    "Use admin account to login",
                    style = Theme.typography.titleMedium,
                    color =  Theme.colors.contentTertiary,
                    modifier = Modifier.padding(top = 8.dp)
                )
                BpTextField(
                    onValueChange = onUserNameChanged,
                    text = state.username,
                    label = "Username",
                    modifier = Modifier.padding(top = 16.dp)
                )
                BpTextField(
                    onValueChange = onPasswordChanged,
                    text = state.password,
                    label = "Password",
                    keyboardType = KeyboardType.Password,
                    modifier = Modifier.padding(top = 16.dp)
                )
                BpCheckBox(
                    label = "Keep me logged in",
                    isChecked = false,
                    onCheck = {},
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                )
                BpButton(
                    title = "Login",
                    onClick = onClickLogin,
                    modifier = Modifier.padding(top = 24.dp).fillMaxWidth()
                )
            }
        }
    }
}