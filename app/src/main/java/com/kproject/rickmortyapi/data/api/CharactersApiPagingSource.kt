package com.kproject.rickmortyapi.data.api

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kproject.rickmortyapi.data.api.service.ApiService
import com.kproject.rickmortyapi.model.Character

private const val INITIAL_PAGE_INDEX = 1

class CharactersApiPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageNumber = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getCharacters(pageNumber)
            val pageInfo = response.body()
            val characterList = pageInfo?.results

            var nextPageNumber: Int? = null
            if (pageInfo?.info?.next != null) {
                val uri = Uri.parse(pageInfo.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = characterList.orEmpty(),
                prevKey = if (pageNumber == INITIAL_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? = null
}