package org.thechance.common.presentation.restaurant

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.components.EditablePriceBar
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.EditableRatingBar

@Composable
fun RestaurantFilterDropdownMenu(
    onClickRating: (Double) -> Unit,
    onClickPrice: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    expanded: Boolean,
    rating: Double,
    priceLevel: Int
) {
    BpDropdownMenu(
        onDismissRequest = onDismissRequest,
        expanded = expanded,
        shape = RoundedCornerShape(Theme.dimens.space8)
    ) {
        RestaurantFilterDropdownMenuContent(
            rating = rating,
            priceLevel = priceLevel,
            onClickRating = onClickRating,
            onClickPrice = onClickPrice
        )
    }
}


@Composable
private fun RestaurantFilterDropdownMenuContent(
    onClickRating: (Double) -> Unit,
    onClickPrice: (Int) -> Unit,
    rating: Double,
    priceLevel: Int
) {
    Column(
        modifier = Modifier.background(
            color = Theme.colors.surface,
            shape = RoundedCornerShape(Theme.dimens.space8)
        )
    ) {
        Text(
            text = "Filter",
            style = Theme.typography.headline,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(start = Theme.dimens.space24, top = Theme.dimens.space24)
        )
        Text(
            text = "Rating",
            style = Theme.typography.title,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(start = Theme.dimens.space24, top = Theme.dimens.space40)
        )
        EditableRatingBar(
            rating = rating,
            count = 5,
            selectedIcon = painterResource(
                if (isSystemInDarkTheme()) "ic_filled_star_dark.svg" else "ic_filled_star_light.svg"
            ),
            halfSelectedIcon = painterResource(
                if (isSystemInDarkTheme()) "ic_half_filled_star_dark.svg" else "ic_half_filled_star_light.svg"
            ),
            notSelectedIcon = painterResource(
                if (isSystemInDarkTheme()) "ic_star_dark.svg" else "ic_star_light.svg"
            ),
            iconsSize = Theme.dimens.space24,
            iconsPadding = PaddingValues(horizontal = Theme.dimens.space8),
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16)
                .background(color = Theme.colors.background)
                .padding(horizontal = Theme.dimens.space24, vertical = Theme.dimens.space16),
            onClick = { onClickRating(it) }
        )

        Text(
            text = "Price level",
            style = Theme.typography.title,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(start = Theme.dimens.space24, top = Theme.dimens.space32)
        )
        EditablePriceBar(
            priceLevel = priceLevel,
            count = 3,
            icon = painterResource("ic_dollar_sign.svg"),
            enabledIconsColor = Theme.colors.success,
            disabledIconsColor = Theme.colors.disable,
            iconsPadding = PaddingValues(horizontal = Theme.dimens.space8),
            iconsSize = Theme.dimens.space16,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16)
                .background(color = Theme.colors.background)
                .padding(horizontal = Theme.dimens.space24, vertical = Theme.dimens.space16),
            onClick = { onClickPrice(it) }
        )

        Row(
            Modifier.fillMaxWidth().padding(Theme.dimens.space24),
            horizontalArrangement = Arrangement.Center
        ) {
            BpTransparentButton(
                title = "Cancel",
                onClick = {},
                modifier = Modifier.padding(end = Theme.dimens.space16).height(Theme.dimens.space32)
                    .weight(1f)
            )
            BpOutlinedButton(
                title = "Save",
                onClick = {},
                modifier = Modifier.height(Theme.dimens.space32).weight(3f)
            )
        }
    }
}