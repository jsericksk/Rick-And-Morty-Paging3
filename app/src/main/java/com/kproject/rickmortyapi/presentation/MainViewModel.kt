package com.kproject.rickmortyapi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kproject.rickmortyapi.data.repository.CharactersRepository
import com.kproject.rickmortyapi.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> get() = _uiState

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            charactersRepository.getCharacters().cachedIn(viewModelScope).collect { pagingDataCharacter ->
                _uiState.value = _uiState.value.copy(characters = pagingDataCharacter)
            }
        }
    }
}