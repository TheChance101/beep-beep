package org.thechance.common.presentation.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
    selectedCarColor: CarColor?
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.kms),
        modifier = modifier.fillMaxWidth()
    ) {
        colors.forEach {
            val selectedModifier = if (selectedCarColor == it) {
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
                            color = Color(it.hexadecimal),
                            shape = RoundedCornerShape(4.kms)
                        )
                        .border(
                            width = 2.kms,
                            color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(4.kms)
                        )
                        .onClick { onSelectColor(it) }
                )
            }
        }
    }
}
