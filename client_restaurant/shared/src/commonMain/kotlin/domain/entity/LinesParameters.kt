package domain.entity

import com.aay.compose.lineChart.model.LineParameters

data class LinesParameters(
    val linesParameters: List<LineParameters>,
    val xAxisData:List<String>
)

