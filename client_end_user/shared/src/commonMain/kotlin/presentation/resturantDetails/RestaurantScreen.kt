package presentation.resturantDetails

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.modifier.noRippleEffect
import resources.Resources
import util.getStatusBarPadding

object RestaurantScreen :
    BaseScreen<RestaurantScreenModel, RestaurantUIState, RestaurantUIEffect, RestaurantInteractionListener>() {


    override fun onEffect(effect: RestaurantUIEffect, navigator: Navigator) {
        when (effect) {
            is RestaurantUIEffect.onBack -> navigator.pop()
            is RestaurantUIEffect.onGoToDetails -> {}
        }
    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: RestaurantUIState, listener: RestaurantInteractionListener) {


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(Resources.images.placeholder),
                contentDescription = "background"
            )
            Row(
                modifier = Modifier.padding(16.dp).height(56.dp).fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    Modifier.size(40.dp).clip(RoundedCornerShape(Theme.radius.medium))
                        .background(color = Theme.colors.surface)
                        .noRippleEffect { listener.onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Resources.images.iconBack),
                        contentDescription = null,
                        tint = Theme.colors.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(.7f)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(Theme.colors.surface).align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Restaurant Name",
                    style = Theme.typography.headline,
                    color = Theme.colors.contentPrimary
                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 16.dp, end = 16.dp,top=16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Restaurant Name",
//                        style = Theme.typography.headline,
//                        color = Theme.colors.contentPrimary
//                    )
//                    Image(
//                        painter = painterResource(Resources.images.heart),
//                        contentDescription = null,
//                        modifier = Modifier.size(16.dp)
//
//                    )
//
//                }
            }
        }
    }


}
