package com.veselovvv.words.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.veselovvv.words.R
import com.veselovvv.words.data.Word

class RecyclerViewAdapter(
    private val words: List<Word>,
    private val clickListener: (Word) -> Unit
) : RecyclerView.Adapter<WordsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordsViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) =
        holder.bind(words[position], clickListener)

    override fun getItemCount() = words.size
}
