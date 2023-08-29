package org.thechance.common.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.presentation.util.kms


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarColors(
    modifier: Modifier = Modifier,
    colors: List<CarColor>,
    onSelectColor: (CarColor) -> Unit,
    selectedCarColor: CarColor
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.kms)
    ) {
        colors.forEach { carColor ->
            val color = Color(carColor.hexadecimal)
            val selectedModifier = if (selectedCarColor == carColor) {
                Modifier.size(32.kms)
                    .border(
                        width = 2.kms,
                        color = Theme.colors.contentSecondary,
                        shape = RoundedCornerShape(4.kms)
                    ).padding(4.kms)

            } else {
                Modifier.size(32.kms)
                    .padding(4.kms)
            }
            Box(
                modifier = selectedModifier,
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(20.kms)
                        .background(
                            color,
                            shape = RoundedCornerShape(4.kms)
                        )
                        .border(
                            width = 2.kms,
                            color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(4.kms)
                        )
                        .onClick { onSelectColor(carColor) }
                )
            }
        }
    }
}
