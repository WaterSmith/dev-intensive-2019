package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import ru.skillbranch.devintensive.extensions.TimeUnits.*

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.shortFormat(): String = this.format(if (this.isSameDay(Date())) "HH:mm" else "dd.MM.yy")

fun Date.isSameDay(date: Date): Boolean = (this / DAY) == (date / DAY)

fun Date.humanizeDiff(now:Date = Date()): String {
    val diff:Long = now - this
    return when (diff) {
        in (Long.MIN_VALUE until DAY*-360) -> "более чем через год"
        in (DAY*-360 until HOUR*-26) -> "через ${DAY.plural(diff/86400000)}"
        in (HOUR*-26 until HOUR*-22) -> "через день"
        in (HOUR*-22 until MINUTE*-75) -> "через ${HOUR.plural(diff/3600000)}"
        in (MINUTE*-75 until MINUTE*-45) -> "через час"
        in (MINUTE*-45 until SECOND*-75) -> "через ${MINUTE.plural(diff/60000)}"
        in (SECOND*-75 until SECOND*-45) -> "через минуту"
        in (SECOND*-45 until SECOND*-1) -> "через несколько секунд"
        in (SECOND*-1..SECOND*1) -> "только что"
        in (SECOND*1+1..SECOND*45) -> "несколько секунд назад"
        in (SECOND*45+1..SECOND*75) -> "минуту назад"
        in (SECOND*75+1..MINUTE*45) -> "${MINUTE.plural(diff/60000)} назад"
        in (MINUTE*45+1..MINUTE*75) -> "час назад"
        in (MINUTE*75+1..HOUR*22) -> "${HOUR.plural(diff/3600000)} назад"
        in (HOUR*22+1..HOUR*26) -> "день назад"
        in (HOUR*26+1..DAY*360) -> "${DAY.plural(diff/86400000)} назад"
        in (DAY*360+1..Long.MAX_VALUE) -> "более года назад"
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

fun Date.add(value:Number, units: TimeUnits):Date {
    this += units * value
    return this
}

operator fun Date.div(units: TimeUnits):Long {
    return this.time / units.times(1)
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    operator fun times(value: Number):Long {
        return value.toLong() * when(this) {
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