package domain.entity

import kotlinx.datetime.Month

data class Date(
    val day: Int,
    val month: Month,
    val year: Int,
)
