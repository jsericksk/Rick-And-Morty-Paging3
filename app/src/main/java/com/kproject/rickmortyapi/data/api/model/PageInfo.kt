package com.kproject.rickmortyapi.data.api.model

import com.kproject.rickmortyapi.model.Character

data class PageInfoResponse(
    val info: Info,
    val results: List<Character>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)