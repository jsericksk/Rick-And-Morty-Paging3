package com.kproject.rickmortyapi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kproject.rickmortyapi.data.api.CharactersApiPagingSource
import com.kproject.rickmortyapi.data.api.service.ApiService
import com.kproject.rickmortyapi.model.Character
import kotlinx.coroutines.flow.Flow

class CharactersRepositoryImpl(private val apiService: ApiService) : CharactersRepository {

    override suspend fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { CharactersApiPagingSource(apiService) }
        ).flow
    }
}