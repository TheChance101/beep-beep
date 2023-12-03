package presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun LoadingOrderItem() {
    val randomMeal= remember { (1..5).random() }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(Theme.radius.medium))
            .background(Theme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        repeat(randomMeal) {
            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp)
                        .clip(RoundedCornerShape(Theme.radius.medium))
                        .shimmerEffect()
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        8.dp,
                        alignment = Alignment.CenterVertically
                    ),
                ) {
                    Box(
                        modifier = Modifier.height(14.dp)
                            .width((100..200).random().dp)
                            .clip(RoundedCornerShape(Theme.radius.medium))
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier.height(14.dp)
                            .width((50..120).random().dp)
                            .clip(RoundedCornerShape(Theme.radius.medium))
                            .shimmerEffect()
                    )
                }
            }
        }
        BPDashedDivider(
            showDiamondIcon = true,
            modifier = Modifier.padding(top = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Box(
                    modifier = Modifier.padding(bottom = 4.dp).height(14.dp)
                        .width((30..90).random().dp).clip(RoundedCornerShape(Theme.radius.medium)).shimmerEffect()
                )
                Box(
                    modifier = Modifier.height(14.dp).width((30..90).random().dp)
                        .clip(RoundedCornerShape(Theme.radius.medium)).shimmerEffect()
                )
            }
            TextButton(
                onClick = { },
                modifier = Modifier.height(34.dp),
                shape = RoundedCornerShape(Theme.radius.small),
                border = BorderStroke(
                    width = 1.dp,
                    color = Theme.colors.contentBorder
                ),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier.height(14.dp).width(50.dp)
                        .clip(RoundedCornerShape(Theme.radius.medium)).shimmerEffect()
                )
            }
        }
    }
}