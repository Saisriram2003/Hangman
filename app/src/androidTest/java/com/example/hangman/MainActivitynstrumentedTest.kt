package com.example.hangman

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    var LIST_ITEM = 1
    var KEY_LIST = listOf<Key>(Key('A',true),Key('B',true),Key('C',true),Key('D',true),Key('E',true),Key('F',true),Key('G',true),Key('H',true),Key('I',true),Key('J',true),Key('K',true),Key('L',true),Key('M',true),Key('N',true),Key('O',true),Key('P',true),Key('Q',true),Key('R',true),Key('S',true),Key('T',true),Key('U',true),Key('V',true),Key('W',true),Key('X',true),Key('Y',true),Key('Z',true))
    @Before
    fun setUp() {
        scenario = launch(MainActivity::class.java)
    }
    @After
    fun tearDown() {
        scenario.close()
    }
//    Show recyclerview
    @Test
    fun keyboardShown(){
        onView(withId(R.id.keyboard_rv)).check(matches(isDisplayed()))
    }

    @Test
    fun checkItemInList(){
        onView(withId(R.id.keyboard_rv)).perform(actionOnItemAtPosition<KeyboardHolder>(LIST_ITEM, click()))
    }

    @Test
    fun testRestartBtn(){
        onView(withId(R.id.restart_button)).perform(click())
        onView(withId(R.id.guessedWord)).check(matches(withText("_ _ _ _ _ _")));
    }
}