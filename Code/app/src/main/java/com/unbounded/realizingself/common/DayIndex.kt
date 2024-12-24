package com.unbounded.realizingself.common
enum class DayIndex(val index: Int) {
    Sun(0),
    Mon(1),
    Tue(2),
    Wed(3),
    Thu(4),
    Fri(5),
    Sat(6);

    companion object {
        fun getDay(index: Int): String {
            return values().find { it.index == index }?.name ?: "Unknown"
        }
    }
}

fun main() {
    println(DayIndex.getDay(0)) // Output: SUNDAY
    println(DayIndex.getDay(1)) // Output: MONDAY
    println(DayIndex.getDay(2)) // Output: TUESDAY
    println(DayIndex.getDay(3)) // Output: WEDNESDAY
    println(DayIndex.getDay(4)) // Output: THURSDAY
    println(DayIndex.getDay(5)) // Output: FRIDAY
    println(DayIndex.getDay(6)) // Output: SATURDAY
    println(DayIndex.getDay(7)) // Output: Unknown (for invalid index)
}
