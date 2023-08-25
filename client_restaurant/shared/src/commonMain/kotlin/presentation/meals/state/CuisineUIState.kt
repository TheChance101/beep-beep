package presentation.meals.state

import domain.entity.Cuisine

data class CuisineUIState(
    val id: String = "",
    val name: String = "",
)

fun List<Cuisine>.toUIState(): List<CuisineUIState> {
    val cuisines = mutableListOf<CuisineUIState>()
    cuisines.add(CuisineUIState("", "All"))
    cuisines.addAll(map { it.toUIState() })
    return cuisines.toList()
}


fun Cuisine.toUIState(): CuisineUIState {
    return CuisineUIState(
        id = id,
        name = name,
    )
}
