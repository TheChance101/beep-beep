package domain.entity

import com.aay.compose.barChart.model.BarParameters

data class BarsParameters(
    val barsParameters: List<BarParameters>,
    val xAxisData:List<String>
)
