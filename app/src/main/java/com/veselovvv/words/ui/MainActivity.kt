package com.veselovvv.words.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.veselovvv.words.R
import com.veselovvv.words.data.WordRepository
import com.veselovvv.words.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val factory = WordViewModelFactory(WordRepository.Base(application))
        wordViewModel = ViewModelProvider(this, factory)[WordViewModel::class.java]

        with(binding) {
            myViewModel = wordViewModel
            lifecycleOwner = this@MainActivity
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        wordViewModel.observeWords(this) { words ->
            binding.recyclerView.adapter = RecyclerViewAdapter(words) { selectedItem ->
                wordViewModel.initUpdateAndDelete(selectedItem)
            }
        }

        wordViewModel.observeMessage(this) { event ->
            event.getContentIfNotHandled()?.let { text ->
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
