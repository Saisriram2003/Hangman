package com.example.hangman

import androidx.lifecycle.SavedStateHandle
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class KeyboardUnitTest {

    var keyboardViewModel = KeyboardViewModel(SavedStateHandle())
    @Test
    fun keyboardReset() {
        var keyboardViewModel = KeyboardViewModel(SavedStateHandle())
        keyboardViewModel.numTries = 3
        keyboardViewModel.correctWord = "Fierce"
        keyboardViewModel.reset()
        assertEquals(0, keyboardViewModel.numTries)
        assertEquals("______", keyboardViewModel.currentGuessedWord)
    }
    @Test
    fun updateGuessedWord() {
        var keyboardViewModel = KeyboardViewModel(SavedStateHandle())
        keyboardViewModel.currentGuessedWord = "______"
        keyboardViewModel.correctWord = "Fierce"
        keyboardViewModel.updateGuessedWord('F')
        assertEquals("F_____", keyboardViewModel.currentGuessedWord)
    }
}