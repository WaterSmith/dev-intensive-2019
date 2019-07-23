package ru.skillbranch.devintensive

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.skillbranch.devintensive.models.Bender
import ru.skillbranch.devintensive.ui.profile.ProfileActivity

@RunWith(AndroidJUnit4::class)
class Task3 {
    @Rule
    @JvmField
    val rule = ActivityTestRule(ProfileActivity::class.java)

    @Test
    fun listenAnswerHalfPosTest(){
        val bender = Bender()
        assertEquals("Как меня зовут?", bender.question.question)
        var response = bender.listenAnswer("Валя Голубкова")
        assertEquals("Это неправильный ответ\nКак меня зовут?", response.first)
        response = bender.listenAnswer("Bender")
        assertEquals("Отлично - ты справился\nНазови мою профессию?", response.first)
        response = bender.listenAnswer("актёр мультфильма")
        assertEquals("Это неправильный ответ\nНазови мою профессию?", response.first)
        response = bender.listenAnswer("а")
        assertEquals("Это неправильный ответ\nНазови мою профессию?", response.first)
        response = bender.listenAnswer("сгибальщик")
        assertEquals("Отлично - ты справился\nИз чего я сделан?", response.first)
        response = bender.listenAnswer("iron")
        assertEquals("Отлично - ты справился\nКогда меня создали?", response.first)
        response = bender.listenAnswer("2993")
        assertEquals("Отлично - ты справился\nМой серийный номер?", response.first)
        response = bender.listenAnswer("2716057")
        assertEquals("Отлично - ты справился\nНа этом все, вопросов больше нет", response.first)
    }

    @Test
    fun listenAnswerNegativeTest1(){
        val bender = Bender()
        assertEquals("Как меня зовут?", bender.question.question)
        var response = bender.listenAnswer("Валя Голубкова")
        assertEquals("Это неправильный ответ\nКак меня зовут?", response.first)
        response = bender.listenAnswer("Bender")
        assertEquals("Отлично - ты справился\nНазови мою профессию?", response.first)
        response = bender.listenAnswer("актёр мультфильма")
        assertEquals("Это неправильный ответ\nНазови мою профессию?", response.first)
        response = bender.listenAnswer("хозяин казино с блэкджеком и ...")
        assertEquals("Это неправильный ответ\nНазови мою профессию?", response.first)
        response = bender.listenAnswer("сгибальщик")
        assertEquals("Отлично - ты справился\nИз чего я сделан?", response.first)
        response = bender.listenAnswer("iron")
        assertEquals("Отлично - ты справился\nКогда меня создали?", response.first)
        response = bender.listenAnswer("2993")
        assertEquals("Отлично - ты справился\nМой серийный номер?", response.first)
        response = bender.listenAnswer("2716054")
        assertEquals("Это неправильный ответ. Давай все по новой\nКак меня зовут?", response.first)
        response = bender.listenAnswer("Bender")
        assertEquals("Отлично - ты справился\nНазови мою профессию?", response.first)
    }

    @Test
    fun listenAnswerNegativeTest2(){
        val bender = Bender()
        assertEquals("Как меня зовут?", bender.question.question)
        var response = bender.listenAnswer("Фрай")
        assertEquals("Это неправильный ответ\nКак меня зовут?", response.first)
        response = bender.listenAnswer("Зоя")
        assertEquals("Это неправильный ответ\nКак меня зовут?", response.first)
        response = bender.listenAnswer("Бандарчук")
        assertEquals("Это неправильный ответ\nКак меня зовут?", response.first)
        response = bender.listenAnswer("Bomberman")
        assertEquals("Это неправильный ответ. Давай все по новой\nКак меня зовут?", response.first)
    }
}