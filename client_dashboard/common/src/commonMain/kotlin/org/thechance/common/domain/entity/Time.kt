package org.thechance.common.domain.entity

data class Time(val hour: Int, val minute: Int) {
    init {
        require(hour in 0..23) { "Hours must be between 0 and 23" }
        require(minute in 0..59) { "Minutes must be between 0 and 59" }
    }

    override fun toString(): String = String.format("%02d:%02d", hour, minute)

    companion object{
        fun parseToCustomTime(time: String): Time {
            return try {
                Time(time.split(":")[0].toInt(), time.split(":")[1].toInt())
            } catch (e: Exception) {
                throw IllegalStateException("format must be HH:MM")
            }
        }
    }
}

