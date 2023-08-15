package org.thechance.service_taxi.domain.entity

data class Taxi(
    val id: String,
    val plateNumber: String,
    val color: Color,
    val type: String,
    val driverId: String,
    val isAvailable: Boolean = true,
    val seats: Int = 4,
)

enum class Color(val colorNumber: Int) {
    BLACK(0),
    WHITE(1),
    RED(2),
    BLUE(3),
    GREEN(4),
    YELLOW(5),
    ORANGE(6),
    PURPLE(7),
    PINK(8),
    BROWN(9),
    GRAY(10),
    SILVER(11),
    GOLD(12),
    BRONZE(13),
    OTHER(14);

    companion object {
        fun getColorByColorNumber(colorNumber: Int): Color {
            Color.values().forEach { if (it.colorNumber == colorNumber) { return it } }
            return OTHER
        }
    }
}