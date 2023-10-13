package com.example.hangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hangman.databinding.FragmentKeyboardBinding


class KeyboardFragment : Fragment() {

    private var _binding: FragmentKeyboardBinding? = null
    private val binding get() = checkNotNull(_binding) {
        "Cannot access binding before onCreateView() or after onDestroyView()"
    }

    private val keyboardViewModel: KeyboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKeyboardBinding.inflate(inflater, container, false)

        binding.keyboardRecyclerView.layoutManager = GridLayoutManager(context, 7)

        val keyboard = keyboardViewModel.keys
        val adapter = KeyboardAdapter(keyboard)
        binding.keyboardRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}