package com.example.hangman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.databinding.ListItemKeyBinding

//class KeyboardHolder(private val binding: ListItemKeyBinding) : RecyclerView.ViewHolder(binding.root) {
//    fun bind(key: Key) {
//        binding.key.text = key.letter.toString()
//
//        if (key.isAvailable) {
//            binding.keyBackground.setBackgroundResource(R.drawable.character_key_available)
//        } else {
//            binding.keyBackground.setBackgroundResource(R.drawable.character_key_unavailable)
//        }
//
////        binding.root.setOnClickListener {
////            Toast.makeText(binding.root.context, "$key pressed!", Toast.LENGTH_SHORT).show()
////            key.isAvailable = false
////            binding.keyBackground.setBackgroundResource(R.drawable.character_key_unavailable)
////        }
//    }
//}


class KeyboardAdapter (var keys: List<Key>,val listener: (Key) -> Unit) : RecyclerView.Adapter<KeyboardAdapter.KeyboardHolder>() {
    inner class KeyboardHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_key, parent, false)
        return KeyboardHolder(view)
    }

    override fun getItemCount() = 26


    override fun onBindViewHolder(holder: KeyboardHolder, position: Int) {
        val key = keys[position]
        holder.itemView.findViewById<TextView>(R.id.key).text = key.letter.toString()
        holder.itemView.setOnClickListener(){
            listener(key)
            if (key.isAvailable) {
                holder.itemView.findViewById<View>(R.id.key_bg).setBackgroundResource(R.drawable.character_key_available)
            } else {
                holder.itemView.findViewById<View>(R.id.key_bg).setBackgroundResource(R.drawable.character_key_unavailable)
            }
        }
    }



}