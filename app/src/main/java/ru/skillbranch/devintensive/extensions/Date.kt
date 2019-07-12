package ru.skillbranch.devintensive.extensions

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.humanizeDiff(now:Date = Date()): String {
    val diff:Long = now - this
    return when (diff) {
        in (Long.MIN_VALUE..TimeUnits.DAY*-360-1) -> "более чем через год"
        in (TimeUnits.DAY*-360..TimeUnits.HOUR*-26-1) -> "через ${TimeUnits.DAY.plural(diff/86400000)}"
        in (TimeUnits.HOUR*-26..TimeUnits.HOUR*-22-1) -> "через день"
        in (TimeUnits.HOUR*-22..TimeUnits.MINUTE*-75-1) -> "через ${TimeUnits.HOUR.plural(diff/3600000)}"
        in (TimeUnits.MINUTE*-75..TimeUnits.MINUTE*-45-1) -> "через час"
        in (TimeUnits.MINUTE*-45..TimeUnits.SECOND*-75-1) -> "через ${TimeUnits.MINUTE.plural(diff/60000)}"
        in (TimeUnits.SECOND*-75..TimeUnits.SECOND*-45-1) -> "через минуту"
        in (TimeUnits.SECOND*-45..TimeUnits.SECOND*-1-1) -> "через несколько секунд"
        in (TimeUnits.SECOND*-1..TimeUnits.SECOND*1) -> "только что"
        in (TimeUnits.SECOND*1+1..TimeUnits.SECOND*45) -> "несколько секунд назад"
        in (TimeUnits.SECOND*45+1..TimeUnits.SECOND*75) -> "минуту назад"
        in (TimeUnits.SECOND*75+1..TimeUnits.MINUTE*45) -> "${TimeUnits.MINUTE.plural(diff/60000)} назад"
        in (TimeUnits.MINUTE*45+1..TimeUnits.MINUTE*75) -> "час назад"
        in (TimeUnits.MINUTE*75+1..TimeUnits.HOUR*22) -> "${TimeUnits.HOUR.plural(diff/3600000)} назад"
        in (TimeUnits.HOUR*22+1..TimeUnits.HOUR*26) -> "день назад"
        in (TimeUnits.HOUR*26+1..TimeUnits.DAY*360) -> "${TimeUnits.DAY.plural(diff/86400000)} назад"
        in (TimeUnits.DAY*360+1..Long.MAX_VALUE) -> "более года назад"
        else -> "никогда"
    }
}

operator fun Date.minus(anoterDate:Date):Long = this.time - anoterDate.time

operator fun Date.plusAssign(interval: Long) {
    this.time += interval
}

operator fun Date.minusAssign(interval: Long) {
    this.time -= interval
}

fun Date.add(value:Long, units: TimeUnits):Date {
    this += units * value
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
        val absvalue = abs(value)
        return if (absvalue%10==1L && absvalue%100!=11L) 0
        else if (absvalue%10>=2L && absvalue%10<=4L && (absvalue%100<10L || absvalue%100>=20L)) 1
        else 2
    }
}