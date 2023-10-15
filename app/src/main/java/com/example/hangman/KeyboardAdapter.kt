package com.example.hangman

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.databinding.ActivityMainBinding
import com.example.hangman.databinding.ListItemKeyBinding
import kotlin.random.Random

private const val TAG = "KeyboardAdapter"

class KeyboardHolder(private val binding: ListItemKeyBinding,  private val keyboardViewModel: KeyboardViewModel, private var mainBinding: ActivityMainBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindUIFor(key: Key) {
        binding.key.text = key.letter.toString()

        if (key.isAvailable) {
            binding.keyBg.setBackgroundResource(R.drawable.character_key_available)
        } else {
            binding.keyBg.setBackgroundResource(R.drawable.character_key_unavailable)
        }

        binding.root.setOnClickListener {
            if (isInCorrectWord(key.letter)) {
                updateGuessedWord(keyboardViewModel.currentGuessedWord)
                invalid(key)
            } else if (key.isAvailable) {
                Toast.makeText(binding.root.context, "incorrect", Toast.LENGTH_SHORT).show()
                keyboardViewModel.numTries++
                setImage(keyboardViewModel.numTries)
                invalid(key)
            } else {
                Toast.makeText(binding.root.context, "Already guessed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun invalid(key: Key) {
        binding.keyBg.setBackgroundResource(R.drawable.character_key_unavailable)
        key.isAvailable = false
    }

    private fun isInCorrectWord(letter: Char): Boolean{
        Log.d(TAG, "Letter: $letter")
        Log.d(TAG, "Correct Word: ${keyboardViewModel.correctWord}")
        var newGuessedWord = keyboardViewModel.currentGuessedWord
        var letterFound = keyboardViewModel.correctWord.contains(letter, true)

        for(i in keyboardViewModel.correctWord.indices){
            if(keyboardViewModel.correctWord[i].equals(letter,true)){
                newGuessedWord = newGuessedWord.substring(0, i) + letter + newGuessedWord.substring(i + 1)
            }
        }

        keyboardViewModel.currentGuessedWord = newGuessedWord
        return letterFound
    }

    private fun updateGuessedWord(currentGuessedWord: String){
        val charArray: CharArray = currentGuessedWord.toCharArray()
        mainBinding.guessedWord.text = charArray.joinToString(separator = " ")
    }

    fun setImage(numTries: Int){
        when(numTries){
            0 -> mainBinding.hangmanView.setImageResource(R.drawable.move0)
            1 -> mainBinding.hangmanView.setImageResource(R.drawable.move1)
            2 -> mainBinding.hangmanView.setImageResource(R.drawable.move2)
            3 -> mainBinding.hangmanView.setImageResource(R.drawable.move3)
            4 -> mainBinding.hangmanView.setImageResource(R.drawable.move4)
            5 -> mainBinding.hangmanView.setImageResource(R.drawable.move5)
            6 -> mainBinding.hangmanView.setImageResource(R.drawable.move6)
        }
    }
}


class KeyboardAdapter (private val keyboardViewModel: KeyboardViewModel, private var mainBinding: ActivityMainBinding) : RecyclerView.Adapter<KeyboardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemKeyBinding.inflate(inflater, parent, false)
        return KeyboardHolder(binding, keyboardViewModel, mainBinding)
    }

    override fun getItemCount() = 26


    override fun onBindViewHolder(holder: KeyboardHolder, position: Int) {
        val key = keyboardViewModel.keys[position]
        holder.bindUIFor(key)
    }
}