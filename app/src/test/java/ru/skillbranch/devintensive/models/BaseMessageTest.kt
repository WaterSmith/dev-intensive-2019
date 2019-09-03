package ru.skillbranch.devintensive.models

import org.junit.Assert.*
import org.junit.Test
import ru.skillbranch.devintensive.extensions.BaseMessageAbstractFactory
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import java.util.*

class BaseMessageTest {
    @Test
    fun makeMessageTest(){
        val user = User.makeUser("Иван Кузьмин")
        var message = BaseMessageAbstractFactory.makeMessage(user, Chat("0","Test chat"), Date(), "text", "any text message")
        assertTrue(message is TextMessage)
        assertNotEquals("-1", message.id)
        assertTrue(message.from === user)
        message = BaseMessageAbstractFactory.makeMessage(user, Chat("0","Test chat"), Date(), "image", "https://anyurl.com")
        assertTrue(message is ImageMessage)
        assertNotEquals("-1", message.id)
        assertFalse(message.isIncoming)
        assertEquals((message as ImageMessage).image, "https://anyurl.com")
    }
}