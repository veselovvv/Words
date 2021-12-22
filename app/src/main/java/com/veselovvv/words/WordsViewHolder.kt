package com.veselovvv.words

import androidx.recyclerview.widget.RecyclerView
import com.veselovvv.words.databinding.ListItemBinding
import com.veselovvv.words.db.Word

class WordsViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(word: Word) {
        binding.wordTextView.text = word.word
        binding.translateTextView.text = word.translate
    }
}