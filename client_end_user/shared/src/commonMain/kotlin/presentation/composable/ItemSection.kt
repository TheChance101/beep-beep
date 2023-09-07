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
    header: String,
    titles: List<String>,
    priceLevels: List<PriceLevel>,
    painters: List<Painter>,
    ratings: List<Double>,
    modifier: Modifier = Modifier,
    hasOffer: Boolean = false,
    offers: List<String> = emptyList(),
    hasDeliveryPrice: Boolean = false,
    deliveryPrices: List<String> = emptyList()
) {
    Column(modifier = modifier) {
        SectionHeader(header, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(titles.size) { index ->
                BpImageCard(
                    title = titles[index],
                    painter = painters[index],
                    priceLevel = priceLevels[index],
                    hasOffer = hasOffer,
                    offer = if (offers.isNotEmpty()) offers[index] else "",
                    hasDeliveryPrice = hasDeliveryPrice,
                    deliveryPrice = if (deliveryPrices.isNotEmpty()) deliveryPrices[index] else "",
                    rate = ratings[index]
                )
            }
        }
    }


}