package ru.skillbranch.devintensive

import android.graphics.Rect
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.skillbranch.devintensive.ui.profile.ProfileActivity

@RunWith(AndroidJUnit4::class)
class Task5 {
    @Rule
    @JvmField
    val rule = ActivityTestRule(ProfileActivity::class.java)

    @Test
    fun onDoneBtnTest(){
        Espresso.onView(ViewMatchers.withId(rule.activity.messageEt.id)).perform(ViewActions.typeText("Bender"))
        Thread.sleep(2000)

        var rootView = rule.activity.findViewById<View>(android.R.id.content)
        var visibleBounds = Rect()
        rootView.getWindowVisibleDisplayFrame(visibleBounds)
        val openKeyboardHeight = rootView.height - visibleBounds.height()

        Espresso.onView(ViewMatchers.withId(rule.activity.messageEt.id)).perform(ViewActions.pressImeActionButton())

        Thread.sleep(2000)
        rootView.getWindowVisibleDisplayFrame(visibleBounds)
        val closedKeyboardHeight = rootView.height - visibleBounds.height()

        assertTrue(openKeyboardHeight > closedKeyboardHeight)
        assertTrue(rule.activity.messageEt.text.isEmpty())
        assertEquals("Отлично - ты справился\nНазови мою профессию?", rule.activity.textTxt.text)
    }
}