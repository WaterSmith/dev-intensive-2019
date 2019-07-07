package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.humanizeDiff(now:Date = Date()): String {
    //TODO("not implemented")
    val diff:Long = now - this
    return when (diff) {
        in (Long.MIN_VALUE..-31104000001) -> "более чем через год"
        in (-31104000000..-93600001) -> "через ${TimeUnits.DAY.plural(diff/86400000)}"
        in (-93600000..-79200001) -> "через день"
        in (-79200000..-4500001) -> "через ${TimeUnits.HOUR.plural(diff/3600000)}"
        in (-4500000..-2700001) -> "через час"
        in (-2700000..-75001) -> "через ${TimeUnits.MINUTE.plural(diff/60000)}"
        in (-75000..-45001) -> "через минуту"
        in (-45000..-1001) -> "через несколько секунд"
        in (-1000..1000) -> "только что"
        in (1001..45000) -> "несколько секунд назад"
        in (45001..75000) -> "минуту назад"
        in (75001..2700000) -> "${TimeUnits.MINUTE.plural(diff/60000)} назад"
        in (2700001..4500000) -> "час назад"
        in (4500001..79200000) -> "${TimeUnits.HOUR.plural(diff/3600000)} назад"
        in (79200001..93600000) -> "день назад"
        in (93600001..31104000000) -> "${TimeUnits.DAY.plural(diff/86400000)} назад"
        in (31104000001..Long.MAX_VALUE) -> "более года назад"
        else -> "никогда"
    }
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

    fun plural(value:Long):String {
        return "${abs(value)} " + when (this) {
            SECOND -> {when (pluralForm(value)) {
                    0 -> "секунду"
                    1 -> "секунды"
                    else -> "секунд"
                }
            }
            MINUTE -> {when (pluralForm(value)) {
                0 -> "минуту"
                1 -> "минуты"
                else -> "минут"
            }
            }
            HOUR -> {when (pluralForm(value)) {
                0 -> "час"
                1 -> "часа"
                else -> "часов"
            }
            }
            DAY -> {when (pluralForm(value)) {
                0 -> "день"
                1 -> "дня"
                else -> "дней"
            }
            }
        }
    }

    private fun pluralForm(value:Long):Int {
        var absvalue = abs(value)
        return if (absvalue%10==1L && absvalue%100!=11L) 0
        else if (absvalue%10>=2L && absvalue%10<=4L && (absvalue%100<10L || absvalue%100>=20L)) 1
        else 2
    }
}