package domain.entity

enum class TaxiColor(val colorNumber: Long) {
    BLACK(4278190080L),
    WHITE(4294967295L),
    RED(4294901760L),
    BLUE(4278190335L),
    GREEN(4278255360L),
    YELLOW(4294639360L),
    ORANGE(4294944768L),
    PURPLE(4288545023L),
    PINK(4293591295L),
    BROWN(4283578920L),
    GRAY(4290626507L),
    SILVER(4290822336L),
    GOLD(4294956800L),
    BRONZE(4291657522L),
    OTHER(0L);

    companion object {
        fun getColorByColorNumber(colorNumber: Long): TaxiColor {
            TaxiColor.values().forEach {
                if (it.colorNumber == colorNumber) {
                    return it
                }
            }
            return OTHER
        }
    }
}