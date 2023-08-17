package org.thechance.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beepbeep.designSystem.ui.composable.BpTextButton
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun RestaurantFilterDropDownMenu(
    rating: Double,
    priceLevel: Int,
    onClickRating: (Double) -> Unit,
    onClickPrice: (Int) -> Unit
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
            selectedIcon = painterResource("ic_filled_star.svg"),
            halfSelectedIcon = painterResource("ic_half_filled_star.svg"),
            notSelectedIcon = painterResource("ic_star.svg"),
            iconsSize = Theme.dimens.space32,
            iconsPadding = Theme.dimens.space8,
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
            text = "$",
            enabledIconsColor = Theme.colors.success,
            disabledIconsColor = Theme.colors.disable,
            textSize = 24.sp,
            textPadding = 8.dp,
            modifier = Modifier.fillMaxWidth().padding(top = Theme.dimens.space16)
                .background(color = Theme.colors.background)
                .padding(horizontal = Theme.dimens.space24, vertical = Theme.dimens.space16),
            onClick = { onClickPrice(it) }
        )

        Row(
            Modifier.fillMaxWidth().padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            BpTextButton(
                text = "Cancel",
                onClick = {},
                modifier = Modifier.padding(end = Theme.dimens.space16)
            )
            BpTextButton(
                text = "Save",
                onClick = {},
            )
        }
    }
}