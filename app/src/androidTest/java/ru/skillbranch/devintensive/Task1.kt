package ru.skillbranch.devintensive

import android.R
import android.view.View
import android.widget.EditText
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.skillbranch.devintensive.ui.profile.ProfileActivity

@RunWith(AndroidJUnit4::class)
class Task1 {
    @Rule
    @JvmField
    val rule = ActivityTestRule(ProfileActivity::class.java)

    @Test
    fun rotateWithoutIdTest(){
        val editText = findEditTextView(rule.activity.findViewById(R.id.content))
        assertNotNull(editText)
        Espresso.onView(
            ViewMatchers
            .withClassName(Matchers.endsWith("EditText")))
            .perform(ViewActions.typeText("to not disappear!"))

        rotateScreen(rule.activity, true)

        var newEditView = findEditTextView(rule.activity.findViewById(R.id.content))
        assertEquals("to not disappear!", newEditView?.text.toString())
        rotateScreen(rule.activity, false)

        newEditView = findEditTextView(rule.activity.findViewById(R.id.content))
        assertEquals("to not disappear!", newEditView?.text.toString())
    }

    @Test
    fun textFieldHasIdTest(){
        val correctIdName = "et_message"
        val normId = rule.activity.resources.getIdentifier(correctIdName, "id", rule.activity.packageName)
        val view = rule.activity.findViewById<View>(normId)
        assertTrue(view is EditText)
    }

}