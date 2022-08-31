package com.kproject.rickmortyapi.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.kproject.rickmortyapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CharactersAdapter()
        binding.rvCharacterList.adapter = adapter.withLoadStateFooter(
            footer = CharactersLoadStateAdapter(
                retry = { adapter.retry() }
            )
        )

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                with(binding) {
                    pbLoading.isVisible = loadState.refresh is LoadState.Loading
                    rvCharacterList.isVisible =
                            loadState.refresh is LoadState.NotLoading && loadState.refresh !is LoadState.Error
                    llError.isVisible = loadState.refresh is LoadState.Error
                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.uiState.collectLatest { pagingDataCharacter ->
                adapter.submitData(pagingDataCharacter.characters)
            }
        }
    }
}