package ru.skillbranch.devintensive

//import androidx.test.core.app.ActivityScenario
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.google.android.material.chip.Chip
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.UserAdapter
import ru.skillbranch.devintensive.ui.group.GroupActivity
import ru.skillbranch.devintensive.ui.main.MainActivity
import java.lang.Thread.sleep
import java.util.*
import org.hamcrest.CoreMatchers.`is` as Is


@LargeTest
@RunWith(AndroidJUnit4::class)
class Hometask5 {

    /**
     * Проверяется реализация методов класса Chat (unreadableMessageCount(), lastMessageDate(), lastMessageShort())
     */
    @Test
    fun module1() {
        val expectedDate = Date()
        val user = User(
            "0",
            firstName = "John",
            lastName = "Doe",
            avatar = null,
            lastVisit = expectedDate.add(-2, TimeUnits.HOUR)
        )

        val user2 = user.copy(id = "1", lastName = "SilverHand", isOnline = true)
        val expectedMessageShort = "lorem ipsum" to user2.firstName
        val expectedMembers = listOf(user, user2)

        val chat = Chat(
            "0",
            "for test",
            expectedMembers,
            mutableListOf()
        )

        val message1 = TextMessage(
            "0",
            user,
            chat,
            true,
            expectedDate.add(-2, TimeUnits.HOUR),
            false,
            "lorem ipsum"
        )

        val message2 = TextMessage(
            "1",
            user2,
            chat,
            true,
            expectedDate,
            false,
            "lorem ipsum"
        )
        chat.messages = mutableListOf(message1, message2)

        Assert.assertEquals(2, chat.unreadableMessageCount())
        Assert.assertEquals(expectedDate, chat.lastMessageDate())
        Assert.assertEquals(expectedMessageShort, chat.lastMessageShort())
    }

    /**
     * Проверяется реализация архивирования сообщений свайпом
     */
    @Test
    fun module2() {
        val ITERACTION_ITEM_POSITION = 6
        var adapter: ChatAdapter? = null
        val initItemCount: Int
        val finalItemCount: Int

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        activityScenario.onActivity {
            adapter = it.findViewById<RecyclerView>(R.id.rv_chat_list)?.adapter as ChatAdapter
        }

        if (adapter == null) {
            Assert.fail("ChatAdapter must be not null")
        }

        initItemCount = adapter!!.itemCount

        for (position in ITERACTION_ITEM_POSITION downTo 2 step 2) {
            onView(withId(R.id.rv_chat_list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<ChatAdapter.ChatItemViewHolder>(
                        position,
                        swipeLeft()
                    )
                )
        }

        finalItemCount = adapter!!.itemCount

        Assert.assertTrue(
            "Items count in ChatAdapter after archived items must be less then initial items count " +
                    "but more then 0 >> initItemCount: $initItemCount, finalItemCount: $finalItemCount",
            finalItemCount in 1 until initItemCount
        )

    }

    /**
     * Проверяется реализация восстановления из архива при отмене с помощью Snackbar
     */
    @Test
    fun module3() {
        val ITERACTION_ITEM_POSITION = 6
        var adapter: ChatAdapter? = null
        val initItemCount: Int
        val finalItemCount: Int
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        activityScenario.onActivity {
            adapter = it.findViewById<RecyclerView>(R.id.rv_chat_list)?.adapter as ChatAdapter
        }

        if (adapter == null) {
            Assert.fail("ChatAdapter must be not null")
        }
        initItemCount = adapter!!.itemCount
        val expectedText = "Вы точно хотите добавить ${adapter!!.items[ITERACTION_ITEM_POSITION].title} в архив?"


        onView(withId(R.id.rv_chat_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ChatAdapter.ChatItemViewHolder>(
                    ITERACTION_ITEM_POSITION,
                    swipeLeft()
                )
            )


        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(expectedText)))

        onView(withId(com.google.android.material.R.id.snackbar_action))
            .perform(click())

        finalItemCount = adapter!!.itemCount
        Assert.assertEquals(
            "Items count in ChatAdapter after cancel archiving items must be equals initial " +
                    "items count >> initItemCount: $initItemCount, finalItemCount: $finalItemCount",
            initItemCount,
            finalItemCount
        )
    }

    /**
     * Проверяется реализация добавления выбранных пользователей в ChipGroup с последующим удалением из нее
     */
    @Test
    fun module4() {
        val ITERACTION_ITEM_POSITION = 4
        var adapter: UserAdapter? = null
        val selectedUsers: MutableMap<Int, UserItem> = mutableMapOf()
        val activityScenario = ActivityScenario.launch(GroupActivity::class.java)

        activityScenario.onActivity {
            adapter = it.findViewById<RecyclerView>(R.id.rv_user_list)?.adapter as UserAdapter
        }

        if (adapter == null) {
            Assert.fail("UserAdapter must be not null")
        }

        for (position in ITERACTION_ITEM_POSITION downTo 0 step 2) {
            selectedUsers[position] = adapter!!.items[position]
            val user = adapter!!.items[position]
            onView(withId(R.id.rv_user_list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(
                        position,
                        click()
                    )
                )

            onView(withId(R.id.rv_user_list))
                .perform(RecyclerViewActions.scrollToPosition<UserAdapter.UserViewHolder>(position))

            onView(
                RecyclerViewMatcher(R.id.rv_user_list).atPositionOnView(
                    position,
                    R.id.iv_selected
                )
            )
                .check(matches(isDisplayed()))

            onView(withTagValue(Is(user.id)))
                .check(matches(isDisplayed()))
        }

        for ((position, user) in selectedUsers) {
            onView(withTagValue(Is(user.id)))
                .perform(ClickCloseIconAction())
                .check(doesNotExist())

            onView(withId(R.id.rv_user_list))
                .perform(RecyclerViewActions.scrollToPosition<UserAdapter.UserViewHolder>(position))

            onView(
                RecyclerViewMatcher(R.id.rv_user_list).atPositionOnView(
                    position,
                    R.id.iv_selected
                )
            )
                .check(matches(not(isDisplayed())))
        }
    }

    /**
     * Проверяется поиск по пользователям в GroupActivity и тестируется сохранение состояния isSelected после
     * очистки строки поиска
     */
    @Test
    fun module5() {
        var adapter: UserAdapter? = null
        val activityScenario = ActivityScenario.launch(GroupActivity::class.java)

        activityScenario.onActivity {
            adapter = it.findViewById<RecyclerView>(R.id.rv_user_list)?.adapter as UserAdapter
        }

        if (adapter == null) {
            Assert.fail("UserAdapter must be not null")
        }

        val filteredLetter = adapter!!.items.first().fullName.substring(0..1)
        val filteredItems = adapter!!.items.filter { it.fullName.contains(filteredLetter, true) }

        onView(withId(R.id.action_search))
            .perform(click())

        onView(isAssignableFrom(EditText::class.java))
            .perform(replaceText(filteredLetter), pressImeActionButton(), pressBack())

        for (position in 0 until filteredItems.size) {
            onView(withId(R.id.rv_user_list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(
                        position,
                        click()
                    )
                )
        }

        onView(isAssignableFrom(EditText::class.java))
            .perform(clearText(), pressImeActionButton(), pressBack())

        filteredItems.forEach { user ->
            val position = adapter!!.items.indexOfFirst { item -> item.id == user.id }

            onView(withId(R.id.rv_user_list))
                .perform(
                    RecyclerViewActions.scrollToPosition<UserAdapter.UserViewHolder>(position)
                )

            onView(
                RecyclerViewMatcher(R.id.rv_user_list)
                    .atPositionOnView(position, R.id.iv_selected)
            )
                .check(matches(isDisplayed()))
            sleep(1000)
        }
    }

    /**
     * Проверяется весь цикл создания и добавления группы в список чатов
     * очистки строки поиска
     */
    @Test
    fun module6() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        var initItemCount = 0
        var finalItemCount = 0

        activityScenario.onActivity {
            initItemCount = it.findViewById<RecyclerView>(R.id.rv_chat_list)?.adapter?.itemCount ?: 0
        }

        onView(withId(R.id.fab))
            .perform(click())

        for (position in 0..3) {
            onView(withId(R.id.rv_user_list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(
                        position,
                        click()
                    )
                )
        }

        onView(withId(R.id.fab))
            .perform(click())

        activityScenario.onActivity {
            finalItemCount = it.findViewById<RecyclerView>(R.id.rv_chat_list)?.adapter?.itemCount ?: 0
        }

        Assert.assertEquals(
            "Items count in ChatAdapter after add items must be more then initial items " +
                    ">> initItemCount: $initItemCount, finalItemCount: $finalItemCount",

            initItemCount + 1,
            finalItemCount
        )
    }
}

class ClickCloseIconAction : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return isAssignableFrom(Chip::class.java)
    }

    override fun getDescription(): String {
        return "click on close icon "
    }

    override fun perform(uiController: UiController, view: View) {
        val chip = view as Chip
        chip.performCloseIconClick()
    }
}

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null
            override fun describeTo(description: Description) {
                var idDescription = Integer.toString(recyclerViewId)
                if (this.resources != null) {
                    idDescription = try {
                        this.resources!!.getResourceName(recyclerViewId)
                    } catch (err: NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            Integer.valueOf(recyclerViewId)
                        )
                    }

                }
                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {
                this.resources = view.resources
                if (childView == null) {
                    val recyclerView = view.rootView.findViewById<View>(recyclerViewId) as RecyclerView
                    if (recyclerView.id == recyclerViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                    } else {
                        return false
                    }
                }
                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView!!.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }
}
