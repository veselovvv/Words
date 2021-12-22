package com.veselovvv.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.veselovvv.words.databinding.ListItemBinding
import com.veselovvv.words.db.Word

class RecyclerViewAdapter(private val words: List<Word>) : RecyclerView.Adapter<WordsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item,
            parent,
            false
        )

        return WordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int {
        return words.size
    }
}