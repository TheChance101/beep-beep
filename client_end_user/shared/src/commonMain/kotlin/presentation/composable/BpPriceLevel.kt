package presentation.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.PriceLevel

@Composable
fun BpPriceLevel(priceLevel: PriceLevel) {
    val startGrayIndex: Int = when (priceLevel) {
        PriceLevel.LOW -> 1
        PriceLevel.MEDIUM -> 2
        PriceLevel.HIGH -> 3
    }

    for (i in 0..2) {
        val color = if (i < startGrayIndex) Theme.colors.success else Theme.colors.disable
        Text("$", style = Theme.typography.body, color = color)
    }

}