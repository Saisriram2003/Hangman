package com.example.hangman

import androidx.lifecycle.ViewModel

class KeyboardViewModel : ViewModel() {

    val keys = mutableListOf<Key>()

    init {
        for (i in 0 until 26) {
            val letter = 'A' + i
            keys += Key(letter, false)
        }
    }
}