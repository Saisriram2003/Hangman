package com.example.hangman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hangman.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val keyboardViewModel: KeyboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (keyboardViewModel.keys.isEmpty()){
            generateNewGame()
        } else {
            val adapter = KeyboardAdapter(keyboardViewModel, this)
            binding.keyboardRv.adapter = adapter
            binding.keyboardRv.setHasFixedSize(true)
            binding.keyboardRv.layoutManager = GridLayoutManager(this, 7)
            updateLife()
            updateGuessedWord()
        }

        Log.d(TAG,"keyboardViewModel.keys = ${keyboardViewModel.keys}")

        binding.restartButton.setOnClickListener {
            generateNewGame()
            updateLife()
        }

        binding.hintButton?.setOnClickListener {
            keyboardViewModel.numHintClicks++

            if (keyboardViewModel.numTries == keyboardViewModel.maxTries - 1) {
                Toast.makeText(this, "Hint not Available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (keyboardViewModel.numHintClicks == 1) {
                Toast.makeText(this, "Hint: ${Words.gameWords[keyboardViewModel.correctWord]}", Toast.LENGTH_SHORT).show()
            } else if (keyboardViewModel.numHintClicks == 2) {
                // cost user a turn
                keyboardViewModel.numTries++

                keyboardViewModel.disableHalfKeys()
                binding.keyboardRv.adapter?.notifyDataSetChanged()

                updateLife()
            } else if (keyboardViewModel.numHintClicks == 3) {
                // check the empty spaces in currentGuessedWord
                // if they are all vowels, Hint not available
                val emptyIndices = keyboardViewModel.currentGuessedWord.indices.filter { keyboardViewModel.currentGuessedWord[it] == '_' }

                var allConstant = true

                val vowels = listOf('A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u')

                emptyIndices.forEach { index ->
                    if (keyboardViewModel.currentGuessedWord[index] !in vowels) {
                        allConstant = false
                    }
                }
                Log.d(TAG, "emptyIndices = $emptyIndices. allVowels = $allConstant")

                if (!allConstant) {
                    Toast.makeText(this, "Hint not Available", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // cost user a turn
                keyboardViewModel.numTries++

                keyboardViewModel.showAllVowel()
                binding.keyboardRv.adapter?.notifyDataSetChanged()

                updateGuessedWord()
                updateLife()
            } else {
                Toast.makeText(this, "No more hints!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun generateNewGame(){
        keyboardViewModel.reset()
        updateLife()
        binding.guessedWord.text = "_ _ _ _ _ _"
        binding.hangmanView.setImageResource(R.drawable.move0)

        val adapter = KeyboardAdapter(keyboardViewModel, this)
        binding.keyboardRv.adapter = adapter
        binding.keyboardRv.setHasFixedSize(true)
        binding.keyboardRv.layoutManager = GridLayoutManager(this, 7)

        Log.d(TAG, keyboardViewModel.correctWord)
    }

    fun updateGuessedWord(){
        val charArray: CharArray = keyboardViewModel.currentGuessedWord.toCharArray()
        binding.guessedWord.text = charArray.joinToString(separator = " ")
    }

    fun updateLife() {
        // Update the life text field
        val remainingLife = keyboardViewModel.maxTries - keyboardViewModel.numTries
        val text = "Life: " + "❤️".repeat(remainingLife)

        // set the image of the hangman
        when(keyboardViewModel.numTries){
            0 -> binding.hangmanView.setImageResource(R.drawable.move0)
            1 -> binding.hangmanView.setImageResource(R.drawable.move1)
            2 -> binding.hangmanView.setImageResource(R.drawable.move2)
            3 -> binding.hangmanView.setImageResource(R.drawable.move3)
            4 -> binding.hangmanView.setImageResource(R.drawable.move4)
            5 -> binding.hangmanView.setImageResource(R.drawable.move5)
            6 -> binding.hangmanView.setImageResource(R.drawable.move6)
        }

        binding.life?.text = text
    }
}
