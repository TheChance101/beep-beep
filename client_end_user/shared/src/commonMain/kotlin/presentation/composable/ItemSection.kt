package presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import domain.entity.PriceLevel

@Composable
fun ItemSection(
    onClickItem: (String) -> Unit = {},
    header: String,
    titles: List<String>,
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
    hasPriceLevel: Boolean = false,
    hasRating: Boolean = false,
    ratings: List<Double> = emptyList(),
    priceLevels: List<PriceLevel> = emptyList(),
    ids: List<String> = emptyList(),
    hasOffer: Boolean = false,
    hasPrice: Boolean = false,
    prices: List<Double> = emptyList(),
    offers: List<String> = emptyList(),
    hasDeliveryPrice: Boolean = false,
    deliveryPrices: List<String> = emptyList(),
) {
    Column(modifier = modifier) {
        SectionHeader(header, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(titles.size) { index ->
                BpImageCard(
                    onClickCard = { onClickItem(ids[index]) },
                    title = titles[index],
                    imageUrl = imageUrls[index],
                    priceLevel = if (priceLevels.isNotEmpty()) priceLevels[index] else PriceLevel.LOW,
                    hasOffer = hasOffer,
                    offer = if (offers.isNotEmpty()) offers[index] else "",
                    hasDeliveryPrice = hasDeliveryPrice,
                    deliveryPrice = if (deliveryPrices.isNotEmpty()) deliveryPrices[index] else "",
                    rate = if (ratings.isNotEmpty()) ratings[index] else 0.0,
                    hasPrice = hasPrice,
                    price = if (prices.isNotEmpty()) prices[index] else 0.0,
                    hasPriceLevel = hasPriceLevel,
                    hasRate = hasRating
                )
            }
        }
    }


}
