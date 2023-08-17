package org.thechance.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.components.RestaurantFilterDropDownMenu


@Composable
fun App() {
    val rating = remember { mutableStateOf(0.0) }
    fun onClickRating(ratings: Double) {
        rating.value = ratings
    }

    val priceLevel = remember { mutableStateOf(1) }
    fun onClickPrice(price: Int) {
        priceLevel.value = price
    }
    BpTheme(false) {
        RestaurantFilterDropDownMenu(
            rating = rating.value,
            priceLevel = priceLevel.value,
            onClickRating = { onClickRating(it) },
            onClickPrice = { onClickPrice(it) })
    }
}
