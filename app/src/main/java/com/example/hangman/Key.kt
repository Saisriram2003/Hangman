package com.example.hangman

data class Key(
    val letter: Char,
    var isAvailable: Boolean = false
)