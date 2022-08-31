package com.kproject.rickmortyapi.model

import androidx.paging.PagingData

data class MainUiState(
    val characters: PagingData<Character> = PagingData.empty()
)