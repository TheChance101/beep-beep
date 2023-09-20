package presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
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


    @Composable
    override fun onRender(state: ProfileUIState, listener: ProfileInteractionListener) {
        whiteCard {
            title( Resources.strings.wallet)
            subTitle("\$30.00",Theme.colors.primary)
            title( Resources.strings.username)
            subTitle("@Ali_ahmed")
            title( Resources.strings.address)
            subTitle("Park gavin, 123 street")
            title( Resources.strings.email)
            subTitle("Email@gmail.com")
        }
    }

    @Composable
    private fun whiteCard(content: @Composable () -> Unit){
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
                .clip(shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.surface)
                .padding(horizontal = 16.dp, vertical = 32.dp)
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