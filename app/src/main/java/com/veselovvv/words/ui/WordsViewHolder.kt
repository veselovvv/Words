package com.veselovvv.words.ui

import androidx.recyclerview.widget.RecyclerView
import com.veselovvv.words.data.Word
import com.veselovvv.words.databinding.ListItemBinding

class WordsViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(word: Word, clickListener: (Word) -> Unit) = with(binding) {
        wordTextView.text = word.word
        translateTextView.text = word.translate
        listItemLayout.setOnClickListener { clickListener(word) }
    }
}
