package presentation.main

data class MainScreenUIState(
    val restaurantName: String = "",
    val isOpen:Boolean = true,
    val isDropdownMenuOpen:Boolean = false,
    val chartItems: List<ChartItem> = emptyList(),
) {

    // todo: Need to handle how date look like
    data class ChartItem(
        val points: List<Pair<String, Int>>, // string is days in week, int is value
        val totalThisWeek: Int,
    )
}
