package com.example.hangman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hangman.databinding.ActivityMainBinding
import kotlin.random.Random

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val keyboardViewModel: KeyboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generateNewGame()

        binding.restartButton?.setOnClickListener {
            generateNewGame()
        }
    }

    private fun generateKeys(): List<Key> {
        val keys = mutableListOf<Key>()
        for (i in 0 until 26) {
            val letter = 'A' + i
            keys += Key(letter, true)
        }
        return keys
    }

    private fun generateNewGame(){
        keyboardViewModel.correctWord = Words.gameWords[Random.nextInt(1, Words.gameWords.size)]

        keyboardViewModel.currentGuessedWord = "______"
        keyboardViewModel.numTries = 0
        keyboardViewModel.keys = generateKeys()

        binding.guessedWord.text = "_ _ _ _ _ _"
        binding.hangmanView.setImageResource(R.drawable.move0)

        val adapter = KeyboardAdapter(keyboardViewModel, binding)
        binding.keyboardRv.adapter = adapter
        binding.keyboardRv.setHasFixedSize(true)
        binding.keyboardRv.layoutManager = GridLayoutManager(this, 7)

        Log.d(TAG, keyboardViewModel.correctWord)
    }
}