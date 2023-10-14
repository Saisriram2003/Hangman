package com.example.hangman

import android.inputmethodservice.Keyboard
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val keyboardViewModel: KeyboardViewModel by viewModels()
    private val maxTries = 6


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (keyboardViewModel.keys.isEmpty()){
            keyboardViewModel.keys = generateKeys()
        }
        val adapter = KeyboardAdapter( keyboardViewModel.keys ){key ->
            if(key.isAvailable){
                play(key.letter)
                key.isAvailable = false
            }
            else{
                Toast.makeText(this, "Already guessed!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.keyboardRv?.adapter = adapter
        binding.keyboardRv?.setHasFixedSize(true)
        binding.keyboardRv?.layoutManager = GridLayoutManager(this, 7)

        generateNewGame()
    }
    fun play(char: Char){
        if(checkLetter(char)){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            keyboardViewModel.numTries++
            getCurrImageState(keyboardViewModel.numTries)
        }
        if(isGameFinished()){
            if(keyboardViewModel.numTries == maxTries){
                Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "You won!", Toast.LENGTH_SHORT).show()
            }
            generateNewGame()
        }
        Log.d("MainActivity","${keyboardViewModel.currentGuessedWord}")

    }
    fun isGameFinished(): Boolean{
        if(keyboardViewModel.numTries == maxTries){
            return true
        }
        else if(keyboardViewModel.currentGuessedWord.equals(keyboardViewModel.correctWord, true)){
            return true
        }
        return false
    }
    fun generateKeys(): List<Key> {
        val keys = mutableListOf<Key>()
        for (i in 0 until 26) {
            val letter = 'A' + i
            keys += Key(letter, true)
        }
        return keys
    }


    fun generateNewGame(){
        keyboardViewModel.correctWord = Words.gameWords[Random.nextInt(1, Words.gameWords.size)]
        keyboardViewModel.currentGuessedWord = "______"
        binding.guessedWord.text = keyboardViewModel.currentGuessedWord
        keyboardViewModel.numTries = 0
        getCurrImageState(keyboardViewModel.numTries)
        updateGuessedWord(keyboardViewModel.currentGuessedWord)
        keyboardViewModel.keys = generateKeys()
        val adapter = KeyboardAdapter( keyboardViewModel.keys ){key ->
            if(key.isAvailable){
                play(key.letter)
                key.isAvailable = false
            }
            else{
                Toast.makeText(this, "Already guessed!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.keyboardRv?.adapter = adapter
        Log.d("MainActivity", keyboardViewModel.correctWord)
    }


    fun getCurrImageState(numTries: Int){
        when(numTries){
            0 -> binding.hangmanView.setImageResource(R.drawable.move0)
            1 -> binding.hangmanView.setImageResource(R.drawable.move1)
            2 -> binding.hangmanView.setImageResource(R.drawable.move2)
            3 -> binding.hangmanView.setImageResource(R.drawable.move3)
            4 -> binding.hangmanView.setImageResource(R.drawable.move4)
            5 -> binding.hangmanView.setImageResource(R.drawable.move5)
            6 -> binding.hangmanView.setImageResource(R.drawable.move6)
        }
    }

    fun checkLetter(letter: Char): Boolean{
        Log.d("MainActivity", "Letter: $letter")
        Log.d("MainActivity", "Correct Word: ${keyboardViewModel.correctWord}")
        var newGuessedWord = ""
        var letterFound = false
        for(i in keyboardViewModel.correctWord.indices){
            Log.d("MainActivity", "Current Letter: ${keyboardViewModel.correctWord[i]}")
            if(keyboardViewModel.correctWord[i].equals(letter,true)){
                newGuessedWord += letter
                letterFound = true
            }else{
                newGuessedWord += keyboardViewModel.currentGuessedWord[i]
            }
        }
        keyboardViewModel.currentGuessedWord = newGuessedWord
        updateGuessedWord(keyboardViewModel.currentGuessedWord)
        return letterFound
    }

    fun updateGuessedWord(currentGuessedWord: String){
        var guessedWordWithSpaces = ""
        for(i in currentGuessedWord.indices){
            guessedWordWithSpaces += currentGuessedWord[i] + " "
        }
        binding.guessedWord.text = guessedWordWithSpaces
    }

}