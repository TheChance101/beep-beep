package org.thechance.common.presentation.taxi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.domain.entity.CarColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarColors(
    modifier: Modifier = Modifier,
    colors: List<CarColor>,
    onSelectColor: (CarColor) -> Unit,
    selectedCarColor: CarColor
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)
    ) {
        items(colors) { carColor ->
            val color = Color(carColor.hexadecimal)

            val selectedModifier = if (selectedCarColor == carColor) {
                Modifier.size(Theme.dimens.space32)
                    .border(
                        width = 2.dp,
                        color = Theme.colors.contentSecondary,
                        shape = RoundedCornerShape(Theme.dimens.space4)
                    ).padding(Theme.dimens.space4)

            } else {
                Modifier.size(Theme.dimens.space32).padding(Theme.dimens.space4)
            }
            Box(
                modifier = selectedModifier,
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(20.dp)
                        .background(color, shape = RoundedCornerShape(Theme.dimens.space4))
                        .border(
                            width = 2.dp,
                            color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(Theme.dimens.space4)
                        )
                        .onClick { onSelectColor(carColor) }
                )
            }
        }
    }
}