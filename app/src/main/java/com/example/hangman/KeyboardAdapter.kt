package com.example.hangman

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.databinding.ListItemKeyBinding

class KeyboardHolder(private val binding: ListItemKeyBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(key: Key) {
        binding.key.text = key.toString()

        if (key.isAvailable) {
            binding.keyBackground.setBackgroundResource(R.drawable.character_key_available)
        } else {
            binding.keyBackground.setBackgroundResource(R.drawable.character_key_unavailable)
        }

        binding.root.setOnClickListener {
            // TODO: implement character key press
            Toast.makeText(binding.root.context, "$key pressed!", Toast.LENGTH_SHORT).show()
        }
    }
}

class KeyboardAdapter (private val keys: List<Key>) : RecyclerView.Adapter<KeyboardHolder>() {
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