package com.example.hangman

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.gridlayout.widget.GridLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.databinding.ListItemKeyBinding

class KeyboardHolder(private val binding: ListItemKeyBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(letter: Char) {
        binding.key.text = letter.toString()

        binding.root.setOnClickListener {
            // TODO: implement character key press
            Toast.makeText(binding.root.context, "$letter pressed!", Toast.LENGTH_SHORT).show()
        }
    }
}

class KeyboardAdapter (private val keys: List<Char>) : RecyclerView.Adapter<KeyboardHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemKeyBinding.inflate(inflater, parent, false)
        return KeyboardHolder(binding)
    }

    override fun getItemCount() = 26

    override fun onBindViewHolder(holder: KeyboardHolder, position: Int) {
        val key = keys[position]
        holder.bind(key)
    }

}