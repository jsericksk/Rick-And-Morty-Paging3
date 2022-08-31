package com.kproject.rickmortyapi.di

import com.kproject.rickmortyapi.data.api.service.ApiService
import com.kproject.rickmortyapi.data.repository.CharactersRepository
import com.kproject.rickmortyapi.data.repository.CharactersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(ApiService.API_BASE_URL)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesCharactersRepository(apiService: ApiService): CharactersRepository {
        return CharactersRepositoryImpl(apiService)
    }
}