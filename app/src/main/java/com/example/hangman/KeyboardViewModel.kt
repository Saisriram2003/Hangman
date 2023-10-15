package com.example.hangman

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


const val KEYS = "Keys"
const val TRIES = "Tries"
const val CORRECT_WORD = "CorrectWord"
const val GUESSED_WORD = "GuessedWord"
const val IS_GAME_FINISHED = "isGameFinished"

class KeyboardViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var keys : List<Key> get() = savedStateHandle.get(KEYS) ?: listOf()
        set(value) = savedStateHandle.set(KEYS, value)
    var numTries : Int get() = savedStateHandle.get(TRIES) ?: 0
        set(value) = savedStateHandle.set(TRIES, value)
    var correctWord : String get() = savedStateHandle.get(CORRECT_WORD) ?: "hi"
        set(value) = savedStateHandle.set(CORRECT_WORD, value)
    var currentGuessedWord : String get() = savedStateHandle.get(GUESSED_WORD) ?: ""
        set(value) = savedStateHandle.set(GUESSED_WORD, value)

    val maxTries = 6

    var isGameFinished : Boolean get() = numTries == maxTries || currentGuessedWord == correctWord
        set(value) = savedStateHandle.set(IS_GAME_FINISHED, value)
}
