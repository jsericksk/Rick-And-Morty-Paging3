package com.kproject.rickmortyapi

import com.kproject.rickmortyapi.data.api.service.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiResponseTest {

    @Test
    fun testApiResponse() {
        val service = Retrofit.Builder()
            .baseUrl(ApiService.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        runBlocking {
            try {
                val response = service.getCharacters(1)
                if (response.isSuccessful) {
                    response.body()?.let { pageInfoResponse ->
                        for (character in pageInfoResponse.results) {
                            println(
                                "Id: ${character.id}" +
                                        "\nImagem: ${character.image}" +
                                        "\nNome: ${character.name}" +
                                        "\nStatus: ${character.status}" +
                                        "\nEspécie: ${character.species}" +
                                        "\n${"".padEnd(100, '-')}"
                            )
                        }
                    }
                } else {
                    println("There was an error getting the response.")
                }
            } catch (e: Exception) {
                println("Error loading data: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}