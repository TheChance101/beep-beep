package presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.BpImageCard
import presentation.composable.modifier.noRippleEffect
import presentation.composable.modifier.roundedBorderShape
import presentation.resturantDetails.RestaurantScreen
import resources.Resources
import util.getStatusBarPadding
import util.root

class SearchScreen :
    BaseScreen<SearchScreenModel, SearchUiState, SearchUiEffect, SearchInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: SearchUiEffect, navigator: Navigator) {
        when (effect) {
            is SearchUiEffect.NavigateToMeal -> TODO()
            is SearchUiEffect.NavigateToRestaurant -> navigator.root?.push(RestaurantScreen)
        }
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun onRender(state: SearchUiState, listener: SearchInteractionListener) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .padding(getStatusBarPadding())
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            stickyHeader {
                BpSimpleTextField(
                    state.query,
                    hint = Resources.strings.searchHint,
                    hintColor = Theme.colors.contentSecondary,
                    onValueChange = listener::onSearchInputChanged,
                    onClick = {},
                    leadingPainter = painterResource(Resources.images.searchOutlined),
                    modifier = Modifier.background(Theme.colors.background)
                        .padding(horizontal = 16.dp),
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.restaurants) {
                        Restaurant(
                            restaurant = it,
                            onClick = listener::onRestaurantClicked
                        )
                    }
                }
            }

            items(state.meals) {
                MealCard(
                    meal = it,
                    onClick = listener::onMealClicked
                )
            }
        }
    }


    @Composable
    private fun Restaurant(
        restaurant: RestaurantUiState,
        onClick: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier.widthIn(max = 64.dp).noRippleEffect { onClick(restaurant.id) },
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.roundedBorderShape().size(64.dp),
                painter = rememberAsyncImagePainter(restaurant.image),
                contentScale = ContentScale.Crop,
                contentDescription = restaurant.name,
            )

            Text(
                text = restaurant.name,
                style = Theme.typography.body,
                color = Theme.colors.contentPrimary,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


    @Composable
    private fun MealCard(
        meal: MealUiState,
        onClick: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier.fillMaxWidth().noRippleEffect { onClick(meal.id) }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Image(
                modifier = Modifier.roundedBorderShape().size(height = 72.dp, width = 80.dp),
                painter = rememberAsyncImagePainter(meal.image),
                contentScale = ContentScale.Crop,
                contentDescription = meal.name
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = meal.name,
                        style = Theme.typography.title,
                        color = Theme.colors.contentPrimary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Text(
                        text = meal.price,
                        style = Theme.typography.body,
                        color = Theme.colors.primary,
                        maxLines = 1
                    )
                }
                Text(
                    text = meal.restaurantName,
                    style = Theme.typography.body,
                    color = Theme.colors.contentSecondary,
                    maxLines = 1
                )
            }
        }

    }
}
