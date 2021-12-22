package com.veselovvv.words

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veselovvv.words.databinding.ActivityMainBinding
import com.veselovvv.words.db.WordDatabase
import com.veselovvv.words.db.WordRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = WordDatabase.getInstance(application).wordDAO
        val repository = WordRepository(dao)
        val factory = WordViewModelFactory(repository)
        wordViewModel = ViewModelProvider(this, factory).get(WordViewModel::class.java)

        binding.myViewModel = wordViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayWordsList()
    }

    private fun displayWordsList() {
        wordViewModel.words.observe(this, Observer {
            binding.recyclerView.adapter = RecyclerViewAdapter(it)
        })
    }
}