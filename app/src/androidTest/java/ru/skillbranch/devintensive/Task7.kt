package ru.skillbranch.devintensive

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.skillbranch.devintensive.extensions.isKeyboardClosed
import ru.skillbranch.devintensive.extensions.isKeyboardOpen

@RunWith(AndroidJUnit4::class)
class Task7 {
    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun isKeyboardClosedTest(){
        Thread.sleep(2000)
        assertTrue(rule.activity.isKeyboardClosed())
    }

    @Test
    fun isKeyboardOpenTest(){
        Espresso.onView(ViewMatchers.withId(rule.activity.messageEt.id)).perform(ViewActions.typeText("something"))
        Thread.sleep(2000)
        assertTrue(rule.activity.isKeyboardOpen())
    }
}