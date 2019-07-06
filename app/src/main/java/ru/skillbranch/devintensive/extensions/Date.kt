package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.humanizeDiff(): String {
    //TODO("not implemented")
    return "not implemented"
}

operator fun Date.minus(anoterDate:Date):Long {
    return this.time - anoterDate.time
}

operator fun Date.plusAssign(interval: Long) {
    this.time += interval
}

operator fun Date.minusAssign(interval: Long) {
    this.time -= interval
}

fun Date.add(value:Long, units: TimeUnits):Date {
    this.time += units * value
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    operator fun times(value: Long):Long {
        return value * when(this) {
            SECOND -> 1000L
            MINUTE -> SECOND * 60
            HOUR -> MINUTE * 60
            DAY -> HOUR * 24
        }
    }
}