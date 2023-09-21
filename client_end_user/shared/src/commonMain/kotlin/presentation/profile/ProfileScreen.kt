package presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.main.MainContainer
import resources.Resources

class ProfileScreen:
    BaseScreen<ProfileScreenModel, ProfileUIState, ProfileUIEffect, ProfileInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: ProfileUIEffect, navigator: Navigator) {
        when(effect){
            is ProfileUIEffect.Logout -> navigator.replaceAll(MainContainer)
        }
    }


    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun onRender(state: ProfileUIState, listener: ProfileInteractionListener) {
        Column (
            modifier = Modifier.fillMaxSize().background(Theme.colors.background)
            .verticalScroll(rememberScrollState()),

        ){
            whiteCard {
                title(Resources.strings.wallet)
                subTitle(state.user?.walletBalance.toString(), Theme.colors.primary)
                title(Resources.strings.username)
                subTitle("@${state.user?.username}")
                title(Resources.strings.address)
                subTitle(state.user?.addresses?.firstOrNull()?.address ?: "")
                title(Resources.strings.email)
                subTitle(state.user?.email ?:"")
            }
            whiteCard {
                BpTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    text = state.fullName,
                    onValueChange = listener::onFullNameChanged,
                    label = Resources.strings.fullName,
                    keyboardType = KeyboardType.Text,
                    errorMessage = state.fullNameErrorMsg,
                    isError = state.isFullNameError,
                )
                BpTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    text = state.phoneNumber,
                    onValueChange = listener::onPhoneNumberChanged,
                    label = Resources.strings.mobileNumber,
                    keyboardType = KeyboardType.Text,
                    errorMessage = state.mobileNumberErrorMsg,
                    isError = state.isPhoneNumberError,
                )
                BpButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    title = Resources.strings.save,
                    enabled = state.isButtonEnabled,
                    onClick = {
                        listener.onSaveProfileInfo()
                    },
                )
            }
            whiteCard {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        painter = painterResource(Resources.images.logout),
                        contentDescription = Resources.strings.logout,
                        tint = Theme.colors.primary,
                    )
                    Text(
                        text = Resources.strings.logout,
                        style = Theme.typography.title,
                        color = Theme.colors.primary,
                    )
                }
            }
        }
    }

    @Composable
    private fun whiteCard(content: @Composable () -> Unit){
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
                .clip(shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.surface)
                .padding(16.dp)
        ) {
            content()
        }
    }

    @Composable
    private fun title(text: String){
        Text(
            text = text,
            style = Theme.typography.caption,
            color= Theme.colors.contentTertiary,
            modifier = Modifier.padding(top= 16.dp,bottom = 8.dp)
        )
    }

    @Composable
    private fun subTitle(text: String,color: Color = Theme.colors.contentPrimary){
        Text(
            text = text,
            color=color,
            style = Theme.typography.body,
        )
    }
}