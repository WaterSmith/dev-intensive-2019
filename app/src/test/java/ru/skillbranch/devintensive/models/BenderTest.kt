package ru.skillbranch.devintensive.models

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class BenderTest {
    lateinit var bender:Bender

    @Before
    fun onStart(){
        bender = Bender()
    }

    @Test
    fun makeMessageTest(){
        assertEquals(bender.askQuestion(),"Как меня зовут?")
        var answer = bender.listenAnswer("bender")
        assertEquals("Имя должно начинаться с заглавной буквы\nКак меня зовут?",answer.first)
        assertEquals(Triple(255,255,255),answer.second)
        answer = bender.listenAnswer("Bender")
        assertEquals("Отлично - ты справился\nНазови мою профессию?",answer.first)
        assertEquals(Triple(255,255,255),answer.second)
        answer = bender.listenAnswer("актер")
        assertEquals("Это неправильный ответ\nНазови мою профессию?",answer.first)
        assertEquals(Triple(255,120,0),answer.second)
        assertEquals(bender.askQuestion(),"Назови мою профессию?")
        answer = bender.listenAnswer("сгибальщик")
        assertEquals("Отлично - ты справился\nИз чего я сделан?",answer.first)
        assertEquals(Triple(255,120,0),answer.second)
    }
}