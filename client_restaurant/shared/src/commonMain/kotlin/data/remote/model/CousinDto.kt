package data.remote.model

import domain.entity.Cousin
import domain.entity.Meal

data class CousinDto(
    val id: String,
    val name: String,
)
fun List<CousinDto>.toEntity(): List<Cousin> = map { it.toEntity() }
fun CousinDto.toEntity(): Cousin {
    return Cousin(
        id = id,
        name = name,
    )
}