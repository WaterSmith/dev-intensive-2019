package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test_instance() {
        val user2 = User("2", "John", "Cena")
        assertEquals("2", user2.id)
        assertEquals("John", user2.firstName)
        assertEquals("Cena", user2.lastName)
    }

    @Test
    fun test_factory() {
        val user = User.makeUser("John Wick")
        assertEquals("John", user.firstName)
        assertEquals("Wick", user.lastName)
    }

    @Test
    fun test_decomposition() {
        val user = User.makeUser("John Wick")

        val (id, firstName, lastName) = user

        assertNotEquals("-1", id)
        assertEquals("John", firstName)
        assertEquals("Wick", lastName)
    }

    @Test
    fun test_copy(){
        val user = User.makeUser("John Wick")
        val user2 = user.copy()

        assertEquals(user, user2)
        assertEquals(user.hashCode(), user2.hashCode())
        assertNotEquals(System.identityHashCode(user), System.identityHashCode(user2))
    }

    @Test
    fun test_dateFormat(){
        val date = Date(1562349670921L) //Fri Jul 05 21:01:10 EEST 2019
        assertEquals("05.07.2019",date.format("dd.MM.yyyy"))
        assertEquals("21:01:10 05.07.19",date.format())
        assertEquals("21:01",date.format("HH:mm"))
    }

    @Test
    fun test_Date_add() {
        var date = Date(1562349670921L) //Fri Jul 05 21:01:10 EEST 2019
        assertEquals("05.07.2019",date.format("dd.MM.yyyy"))
        date.add(1, TimeUnits.DAY)
        assertEquals("06.07.2019",date.format("dd.MM.yyyy"))
        date.add(3, TimeUnits.HOUR)
        assertEquals("07.07.2019",date.format("dd.MM.yyyy"))
        date.add(-180, TimeUnits.MINUTE)
        assertEquals("06.07.2019",date.format("dd.MM.yyyy"))
        date.add(-24, TimeUnits.HOUR)
        assertEquals("05.07.2019",date.format("dd.MM.yyyy"))
    }

    @Test
    fun test_data_mapping(){
        val user = User.makeUser("Водаков Сергей")
        println(user)
        val userView = user.toUserView()
        userView.printMe()
        val newUser = user.copy(lastVisit = Date().add(-10, TimeUnits.SECOND))
        newUser.toUserView().printMe()
    }

    @Test
    fun test_message_abstract_factory(){
        val user = User("Водаков Сергей")
        val txtMessage = BaseMessageAbstractFactory.makeMessage(user, Chat("0","Test chat"), payload = "any text message", type = "text", date = Date().add(3, TimeUnits.DAY))
        val imgMessage = BaseMessageAbstractFactory.makeMessage(user, Chat("0", "Test chat"), payload = "any image url", type = "image")

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())
    }

    @Test
    fun test_humaniseDiff(){
        val now = Date()

        var diffDate:Date = now.clone() as Date
        diffDate.add(-360, TimeUnits.DAY)
        assertEquals("360 дней назад",diffDate.humanizeDiff(now))
        diffDate.time--
        assertEquals("более года назад",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(-26, TimeUnits.HOUR)
        assertEquals("день назад",diffDate.humanizeDiff(now))
        diffDate.time--
        assertEquals("1 день назад",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(-22, TimeUnits.HOUR)
        assertEquals("22 часа назад",diffDate.humanizeDiff(now))
        diffDate.time--
        assertEquals("день назад",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(-75, TimeUnits.MINUTE)
        assertEquals("час назад",diffDate.humanizeDiff(now))
        diffDate.time--
        assertEquals("1 час назад",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(-45, TimeUnits.MINUTE)
        assertEquals("45 минут назад",diffDate.humanizeDiff(now))
        diffDate.time--
        assertEquals("час назад",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(-75, TimeUnits.SECOND)
        assertEquals("минуту назад",diffDate.humanizeDiff(now))
        diffDate.time--
        assertEquals("1 минуту назад",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(-45, TimeUnits.SECOND)
        assertEquals("несколько секунд назад",diffDate.humanizeDiff(now))
        diffDate.time--
        assertEquals("минуту назад",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(-1, TimeUnits.SECOND)
        assertEquals("только что",diffDate.humanizeDiff(now))
        diffDate.time--
        assertEquals("несколько секунд назад",diffDate.humanizeDiff(now))

        diffDate.time = now.time
        diffDate.add(360, TimeUnits.DAY)
        assertEquals("через 360 дней",diffDate.humanizeDiff(now))
        diffDate.time++
        assertEquals("более чем через год",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(26, TimeUnits.HOUR)
        assertEquals("через день",diffDate.humanizeDiff(now))
        diffDate.time++
        assertEquals("через 1 день",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(22, TimeUnits.HOUR)
        assertEquals("через 22 часа",diffDate.humanizeDiff(now))
        diffDate.time++
        assertEquals("через день",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(75, TimeUnits.MINUTE)
        assertEquals("через час",diffDate.humanizeDiff(now))
        diffDate.time++
        assertEquals("через 1 час",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(45, TimeUnits.MINUTE)
        assertEquals("через 45 минут",diffDate.humanizeDiff(now))
        diffDate.time++
        assertEquals("через час",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(75, TimeUnits.SECOND)
        assertEquals("через минуту",diffDate.humanizeDiff(now))
        diffDate.time++
        assertEquals("через 1 минуту",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(45, TimeUnits.SECOND)
        assertEquals("через несколько секунд",diffDate.humanizeDiff(now))
        diffDate.time++
        assertEquals("через минуту",diffDate.humanizeDiff(now))
        diffDate.time = now.time
        diffDate.add(1, TimeUnits.SECOND)
        assertEquals("только что",diffDate.humanizeDiff(now))
        diffDate.time++
        assertEquals("через несколько секунд",diffDate.humanizeDiff(now))
    }
    
    @Test
    fun test_plural(){
        assertEquals("2 секунды", TimeUnits.SECOND.plural(-2))
        assertEquals("4 минуты", TimeUnits.MINUTE.plural(4))
        assertEquals("19 часов", TimeUnits.HOUR.plural(19))
        assertEquals("222 дня", TimeUnits.DAY.plural(222))
    }

    @Test
    fun test_truncate(){
        var task = "Bender Bending Rodriguez - дословно \"Сгибальщик Сгибающий Родригес\""
        assertEquals("Bender Bending R...",task.truncate())
        assertEquals("Bender Bending...",task.truncate(15))
        assertEquals("А","А      ".truncate(3))
    }

    @Test
    fun test_initials(){
        assertEquals(null,Utils.toInitials("", " "))
        assertEquals(null,Utils.toInitials(null, null))
        assertEquals("V",Utils.toInitials("Vodakov", null))
        assertEquals("V",Utils.toInitials(null, "Vodakov"))
        assertEquals("VS",Utils.toInitials("Vodakov","Sergey"))
        assertEquals("SV",Utils.toInitials("Sergey", "Vodakov"))
    }

    @Test
    fun test_Builder(){
        val now = Date()
        val user = User.Builder()
            .firstName("Сергей")
            .lastName("Водаков")
            .isOnline(true)
            .lastVisit(now)
            .build()
        assertNotEquals("-1",user.id)
        assertEquals("Сергей",user.firstName)
        assertEquals("Водаков",user.lastName)
        assertTrue(user.isOnline)
        assertEquals(now,user.lastVisit)
    }

    @Test
    fun test_Kotlin_String(){
        println("<p> Какой-то     текст     <a href=\"www.bestpict.com/super.jpg\">Картинка      </a>   еще какой    то <супер> текст </p>".stripHtml())
    }

    @Test
    fun test_transliteration(){
        println(Utils.transliteration("Переквалификация!"))
        println(Utils.transliteration("ПЕРЕКВАЛИФИКАЦИЯ!"))
        println(Utils.transliteration("Сергей ВОДАКОВ!","_"))
    }
}

