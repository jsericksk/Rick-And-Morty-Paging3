package com.kproject.rickmortyapi.data.api.service

import com.kproject.rickmortyapi.data.api.model.PageInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/character/")
    suspend fun getCharacters(@Query("page") page: Int): Response<PageInfoResponse>

    companion object {
        const val API_BASE_URL = "https://rickandmortyapi.com/"
    }
}