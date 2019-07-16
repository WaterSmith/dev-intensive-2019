package ru.skillbranch.devintensive

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.skillbranch.devintensive.models.Bender

@RunWith(AndroidJUnit4::class)
class Task2 {
    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun listenAnswerPositiveTest1(){
        val bender = Bender()
        assertEquals("Как меня зовут?", bender.question.question)
        var response = bender.listenAnswer("Bender")
        assertEquals("Отлично - ты справился\nНазови мою профессию?", response.first)
        response = bender.listenAnswer("bender")
        assertEquals("Отлично - ты справился\nИз чего я сделан?", response.first)
        response = bender.listenAnswer("iron")
        assertEquals("Отлично - ты справился\nКогда меня создали?", response.first)
        response = bender.listenAnswer("2993")
        assertEquals("Отлично - ты справился\nМой серийный номер?", response.first)
        response = bender.listenAnswer("2716057")
        assertEquals("Отлично - ты справился\nНа этом все, вопросов больше нет", response.first)
    }

    @Test
    fun listenAnswerPositiveTest2(){
        val bender = Bender()
        assertEquals("Как меня зовут?", bender.question.question)
        var response = bender.listenAnswer("Bender")
        assertEquals("Отлично - ты справился\nНазови мою профессию?", response.first)
        response = bender.listenAnswer("сгибальщик")
        assertEquals("Отлично - ты справился\nИз чего я сделан?", response.first)
        response = bender.listenAnswer("дерево")
        assertEquals("Отлично - ты справился\nКогда меня создали?", response.first)
        response = bender.listenAnswer("2993")
        assertEquals("Отлично - ты справился\nМой серийный номер?", response.first)
        response = bender.listenAnswer("2716057")
        assertEquals("Отлично - ты справился\nНа этом все, вопросов больше нет", response.first)
    }

    @Test
    fun saveStateWhenRotateTest(){
        assertEquals("Как меня зовут?", rule.activity.textTxt.text)
        Espresso.onView(ViewMatchers.withId(rule.activity.messageEt.id)).perform(ViewActions.typeText("Bender"))
        Espresso.onView(ViewMatchers.withId(rule.activity.sendBtn.id)).perform(ViewActions.click())
        assertEquals("Отлично - ты справился\nНазови мою профессию?", rule.activity.textTxt.text)
        rotateScreen(rule.activity, true)
        assertEquals("Назови мою профессию?", rule.activity.textTxt.text)
        rotateScreen(rule.activity, false)
        assertEquals("Назови мою профессию?", rule.activity.textTxt.text)
        Espresso.onView(ViewMatchers.withId(rule.activity.messageEt.id)).perform(ViewActions.typeText("bender"))
        Espresso.onView(ViewMatchers.withId(rule.activity.sendBtn.id)).perform(ViewActions.click())
        assertEquals("Отлично - ты справился\nИз чего я сделан?", rule.activity.textTxt.text)
    }
}