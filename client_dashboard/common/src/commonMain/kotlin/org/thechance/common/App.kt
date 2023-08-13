package org.thechance.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import org.thechance.common.components.EditableRatingBar

@Composable
fun App() {
    val rating = remember { mutableStateOf(1.0) }
    fun onClick(newRating: Double) {
        rating.value = newRating
    }
    EditableRatingBar(
        rating = rating.value,
        count = 5,
        selectedIcon = painterResource("ic_filled_star.svg"),
        notSelectedIcon = painterResource("ic_star.svg"),
        halfSelectedIcon = painterResource("ic_half_filled_star.svg"),
        onClick = { onClick(it) }
    )
}
