package com.kproject.rickmortyapi.data.repository

import androidx.paging.PagingData
import com.kproject.rickmortyapi.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(): Flow<PagingData<Character>>
}