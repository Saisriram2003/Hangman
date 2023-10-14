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
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var correctWord : String
    private lateinit var currentGuessedWord : String
    private var numTries = 0
    private var maxTries = 6
    private val keyboardViewModel: KeyboardViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (keyboardViewModel.keys.isEmpty()){
            generateKeys()
        }
        val adapter = KeyboardAdapter( keyboardViewModel.keys ){key ->
            if(key.isAvailable){
                Toast.makeText(this, key.letter.toString(), Toast.LENGTH_SHORT).show()
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

    fun generateKeys() {
        val keys = mutableListOf<Key>()
        for (i in 0 until 26) {
            val letter = 'A' + i
            keys += Key(letter, true)
        }
        keyboardViewModel.keys = keys
    }


    fun generateNewGame(){
        correctWord = Words.gameWords[Random.nextInt(1, Words.gameWords.size)]
        currentGuessedWord = "______"
        binding.guessedWord.text = currentGuessedWord
        numTries = 0
        getCurrImageState(numTries)
        updateGuessedWord(currentGuessedWord)
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

    fun checkLetter(letter: Char){
        var newGuessedWord = ""
        for(i in correctWord.indices){
            if(correctWord[i] == letter){
                newGuessedWord += letter
            }else{
                newGuessedWord += currentGuessedWord[i]
            }
        }
        currentGuessedWord = newGuessedWord
        updateGuessedWord(currentGuessedWord)
    }

    fun updateGuessedWord(currentGuessedWord: String){
        var guessedWordWithSpaces = ""
        for(i in currentGuessedWord.indices){
            guessedWordWithSpaces += currentGuessedWord[i] + " "
        }
        binding.guessedWord.text = guessedWordWithSpaces
    }

}