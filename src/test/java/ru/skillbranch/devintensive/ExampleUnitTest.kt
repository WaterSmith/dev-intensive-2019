package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.*
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

        assertEquals("0", id)
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
        val txtMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "a ny text message", type = "text")
        val imgMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any image url", type = "image")

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())
        //when (txtMessage) {
        //    is TextMessage -> println("this is text message")
        //    is ImageMessage -> println("this is image message")
        //}
    }
}
