package presentation.restaurants

import androidx.paging.PagingData
import androidx.paging.map
import domain.entity.PriceLevel
import domain.entity.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import presentation.base.ErrorState

data class RestaurantsUIState(
    val restaurants: Flow<PagingData<RestaurantUIState>> = emptyFlow(),
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
)


data class RestaurantUIState(
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val priceLevel: PriceLevel = PriceLevel.LOW,
    val rate: Double? = null,
)

fun Restaurant.toUIState() = RestaurantUIState(
    id = id,
    name = name,
    imageUrl = image,
    priceLevel = priceLevel,
    rate = rate
)


fun Flow<PagingData<Restaurant>>.toUIState(): Flow<PagingData<RestaurantUIState>> {
    return this.map { pagingData -> pagingData.map { it.toUIState() } }
}
