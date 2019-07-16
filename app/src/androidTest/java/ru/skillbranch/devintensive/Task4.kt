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
import ru.skillbranch.devintensive.extensions.hideKeyboard

@RunWith(AndroidJUnit4::class)
class Task4 {
    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun hideKeyboardTest(){
        Espresso.onView(ViewMatchers.withId(rule.activity.messageEt.id)).perform(ViewActions.typeText("something"))
        Thread.sleep(2000)
        val rootView = rule.activity.findViewById<View>(android.R.id.content)
        val visibleBounds = Rect()
        rootView.getWindowVisibleDisplayFrame(visibleBounds)
        val openKeyboardHeight = rootView.height - visibleBounds.height()

        rule.activity.hideKeyboard()

        Thread.sleep(2000)
        rootView.getWindowVisibleDisplayFrame(visibleBounds)
        val closedKeyboardHeight = rootView.height - visibleBounds.height()

        assertTrue(openKeyboardHeight > closedKeyboardHeight)
    }
}