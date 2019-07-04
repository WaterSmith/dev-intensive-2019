package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.models.User
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
}
