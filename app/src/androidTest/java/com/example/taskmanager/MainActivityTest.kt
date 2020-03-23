package com.example.taskmanager


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textView = onView(
            allOf(
                withId(R.id.textView_popup_title), withText("Title 20"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Title 20")))

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.title), withText("Add new task"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.editText4_form_title),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("T"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editText5_form_desc), withText("R"),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.editText5_form_desc), withText("R"),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("Qwerty"))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.editText5_form_desc), withText("Qwerty"),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(closeSoftKeyboard())

        val appCompatRadioButton = onView(
            allOf(
                withId(R.id.radio_phone), withText("Phone"),
                childAtPosition(
                    allOf(
                        withId(R.id.radioGroup),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            4
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatRadioButton.perform(click())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.editText6_form_due),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("11-11-1234"), closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.button_add_task), withText("Create Task"),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val imageView = onView(
            allOf(
                withId(R.id.imageView_popup_status),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.textView_popup_title), withText("Test"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Test")))

        val textView3 = onView(
            allOf(
                withId(R.id.textView3_popup_due), withText("1234-11-11"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("1234-11-11")))

        val textView4 = onView(
            allOf(
                withId(R.id.textView2_popup_desc), withText("Qwerty"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Qwerty")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
