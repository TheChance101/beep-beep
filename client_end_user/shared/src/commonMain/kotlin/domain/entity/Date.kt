package domain.entity

data class Date(
    val day: Int,
    val month: Int,
    val year: Int
){
    init{
        require(day in 1 .. 30)
        require(month in 1 .. 12)
    }
}
