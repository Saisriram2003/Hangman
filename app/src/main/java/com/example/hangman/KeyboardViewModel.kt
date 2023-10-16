package com.example.hangman

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import kotlin.random.Random

private const val TAG = "KeyboardViewModel"
const val KEYS = "Keys"
const val TRIES = "Tries"
const val CORRECT_WORD = "CorrectWord"
const val GUESSED_WORD = "GuessedWord"
const val IS_GAME_FINISHED = "isGameFinished"
const val NUM_HINT_CLICKS = "numHintClicks"

class KeyboardViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val maxTries = 6

    var keys : List<Key> get() = savedStateHandle.get(KEYS) ?: listOf()
        set(value) = savedStateHandle.set(KEYS, value)

    var numTries : Int get() = savedStateHandle.get(TRIES) ?: 0
        set(value) = savedStateHandle.set(TRIES, value)
    var correctWord : String get() = savedStateHandle.get(CORRECT_WORD) ?: "hi"
        set(value) = savedStateHandle.set(CORRECT_WORD, value)
    var currentGuessedWord : String get() = savedStateHandle.get(GUESSED_WORD) ?: ""
        set(value) = savedStateHandle.set(GUESSED_WORD, value)

    var numHintClicks : Int get() = savedStateHandle.get(NUM_HINT_CLICKS) ?: 0
        set(value) = savedStateHandle.set(NUM_HINT_CLICKS, value)

    var isGameFinished : Boolean get() = numTries == maxTries || currentGuessedWord == correctWord
        set(value) = savedStateHandle.set(IS_GAME_FINISHED, value)

    private fun getRandomWord(): String {
        val randomIndex = Random.nextInt(0, Words.gameWords.size)
        return Words.gameWords.keys.elementAt(randomIndex)
    }
    fun reset() {
        keys = generateKeys()
        numTries = 0
        correctWord = getRandomWord()
        currentGuessedWord = "______"
        numHintClicks = 0
    }

    private fun generateKeys(): List<Key> {
        val keys = mutableListOf<Key>()
        for (i in 0 until 26) {
            val letter = 'A' + i
            keys += Key(letter, true)
        }
        return keys
    }

    fun disableHalfKeys() {
        // disable half of the remaining keys that is not in the word
        val keys = keys

        val numToDisable = keys.count { it.isAvailable } / 2

        val correctWord = correctWord
        var numDisabled = 0
        for (i in keys.indices) {
            if (keys[i].isAvailable && !correctWord.contains(keys[i].letter, true)) {
                keys[i].isAvailable = false
                numDisabled++
            }
            if (numDisabled == numToDisable) {
                break
            }
        }
    }

    fun showAllVowel() {
        // show all vowel in correct word + disable all vowels in keyboard
        val vowel = listOf('a', 'e', 'i', 'o', 'u')

        for (i in correctWord.indices) {
            if (vowel.contains(correctWord[i])) {
                // replaces the character at the i-th index of currentGuessedWord with the uppercase vowel
                currentGuessedWord = currentGuessedWord.substring(0, i) + correctWord[i].uppercaseChar() + currentGuessedWord.substring(i + 1)
                // disable the vowel key
                val disableIndex = correctWord[i] - 'a'
                keys[disableIndex].isAvailable = false
            }
        }

        Log.d(TAG, "Current guessed word: $currentGuessedWord")
    }

    fun updateGuessedWord(letter: Char) {
        for(i in correctWord.indices){
            if(correctWord[i].equals(letter,true)){
                currentGuessedWord = currentGuessedWord.substring(0, i) + letter + currentGuessedWord.substring(i + 1)
            }
        }
    }
}
