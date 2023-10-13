package com.example.hangman

import androidx.lifecycle.ViewModel
import com.example.hangman.databinding.FragmentKeyboardBinding

class KeyboardViewModel : ViewModel() {

    val letters = mutableListOf<Char>()

    init {
        for (i in 0 until 26) {
            val letter = 'A' + i
            letters += letter
        }
    }
}