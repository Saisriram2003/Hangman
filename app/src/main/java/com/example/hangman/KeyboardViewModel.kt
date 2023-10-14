package com.example.hangman

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


const val KEYS = "Keys"

class KeyboardViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var keys : List<Key> get() = savedStateHandle.get(KEYS) ?: listOf()
        set(value) = savedStateHandle.set(KEYS, value)

}
