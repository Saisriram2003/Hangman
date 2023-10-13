package com.example.hangman

import android.R
import android.widget.RelativeLayout
import android.widget.TextView


class KeyView : RelativeLayout() {
    fun setKeyText(text: String?) {
        val textView = findViewById<TextView>(R.id.key_text)
        textView.text = text
    }
}
