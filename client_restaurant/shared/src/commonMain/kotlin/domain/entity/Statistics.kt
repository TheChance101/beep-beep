package domain.entity

data class Statistics(
    val dataSets : List<DataSet>,
    val xAxisData : List<String>
)
data class DataSet(
    val label: String,
    val data :List<Double>
)
