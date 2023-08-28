package domain.entity

data class Time(var hour: Int, var minute: Int, var second: Int) {

    init {
        if (hour !in 0..23 || minute !in 0..59 || second !in 0..59)
            throw IllegalArgumentException("Invalid time")
    }

    fun isBetween(openingTime: Time, closingTime: Time): Boolean {
        return this.hour in openingTime.hour..closingTime.hour
    }

    override fun toString(): String {
        return "$hour:$minute:$second"
    }

    companion object {
        fun fromString(time: String): Time {
            val timeList = time.split(":")
            return Time(timeList[0].toInt(), timeList[1].toInt(), timeList[2].toInt())
        }
    }

}