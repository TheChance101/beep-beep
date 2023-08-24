package presentation.restaurant_selection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.main.MainScreen
import resources.Resources

class RestaurantSelectionScreen(private val ownerId: String) : BaseScreen
<RestaurantSelectionScreenModel,
        RestaurantSelectionScreenUIState,
        RestaurantSelectionScreenUIEffect,
        RestaurantSelectionScreenInteractionListener>() {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { RestaurantSelectionScreenModel(ownerId) }
        initScreen(screenModel)
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: RestaurantSelectionScreenUIState,
        listener: RestaurantSelectionScreenInteractionListener
    ) {
        Column(modifier = Modifier.fillMaxSize().background(Theme.colors.primary)) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(.3f)) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Resources.images.backgroundPattern),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Icon(
                    modifier = Modifier.height(66.dp).width(145.dp).align(Alignment.Center),
                    tint = Color.White,
                    painter = painterResource(Resources.images.bpLogo),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(Theme.colors.surface)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(vertical = 40.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = Resources.strings.chooseYourRestaurant,
                        style = Theme.typography.headline.copy(color = Theme.colors.contentPrimary)
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = Resources.strings.pickWhichRestaurant,
                        style = Theme.typography.body.copy(color = Theme.colors.contentTertiary)
                    )

                    LazyColumn(modifier = Modifier.padding(top = 24.dp)) {
                        itemsIndexed(state.restaurants) { index, item ->
                            RestaurantSelectionItem(item, listener)
                            AnimatedVisibility(index != 2) {
                                Divider(Modifier.background(Theme.colors.divider).alpha(0.8f))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onEffect(effect: RestaurantSelectionScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is RestaurantSelectionScreenUIEffect.SelectRestaurant -> navigator.push(MainScreen())
        }
    }

    @Composable
    private fun RestaurantSelectionItem(
        item: RestaurantSelectionScreenUIState.Restaurant,
        listener: RestaurantSelectionScreenInteractionListener
    ) {

        val buttonBackgroundColor by animateColorAsState(if (item.isOpen) Theme.colors.hover else Color.Transparent)
        val buttonContentColor by animateColorAsState(if (item.isOpen) Theme.colors.primary else Theme.colors.disable)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable { listener.onRestaurantItemClick(item.id) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.restaurantName,
                    style = Theme.typography.title.copy(Theme.colors.contentPrimary)
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = item.restaurantNumber,
                    style = Theme.typography.caption.copy(Theme.colors.contentTertiary)
                )
            }
            BpTransparentButton(
                modifier = Modifier.background(buttonBackgroundColor),
                title = if (item.isOpen) Resources.strings.open else Resources.strings.closed,
                enabled = false,
                contentColor = buttonContentColor,
                onClick = {}
            )
        }
    }
}