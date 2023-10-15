package com.example.hangman

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.databinding.ListItemKeyBinding

private const val TAG = "KeyboardAdapter"

class KeyboardHolder(private val binding: ListItemKeyBinding,  private val keyboardViewModel: KeyboardViewModel, private val mainActivity: MainActivity) : RecyclerView.ViewHolder(binding.root) {

    fun bindUIFor(key: Key) {
        binding.key.text = key.letter.toString()

        if (key.isAvailable) {
            binding.keyBg.setBackgroundResource(R.drawable.character_key_available)
        } else {
            binding.keyBg.setBackgroundResource(R.drawable.character_key_unavailable)
        }

        binding.root.setOnClickListener {
            if (key.isAvailable && isInCorrectWord(key.letter)) {
                keyboardViewModel.updateGuessedWord(key.letter)
                mainActivity.updateGuessedWord()
                invalid(key)
            } else if (key.isAvailable) {
                // Toast.makeText(binding.root.context, "incorrect", Toast.LENGTH_SHORT).show()
                keyboardViewModel.numTries++
                mainActivity.updateLife()
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

        return keyboardViewModel.correctWord.contains(letter, true)
    }
}


class KeyboardAdapter (private val keyboardViewModel: KeyboardViewModel, private var mainActivity: MainActivity) : RecyclerView.Adapter<KeyboardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemKeyBinding.inflate(inflater, parent, false)
        return KeyboardHolder(binding, keyboardViewModel, mainActivity)
    }

    override fun getItemCount() = 26

    override fun onBindViewHolder(holder: KeyboardHolder, position: Int) {
        val key = keyboardViewModel.keys[position]
        holder.bindUIFor(key)
    }
}