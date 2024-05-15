package com.adishakti.repohunt.hilt

import com.adishakti.repohunt.data.repository.GitRepositoryRepository
import com.adishakti.repohunt.data.repository.GitRepositoryRepositoryImpl
import com.adishakti.repohunt.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProviderService {

    @Provides
    @Singleton
    fun provideHomeRepository(apiService: ApiService): GitRepositoryRepository {
        return GitRepositoryRepositoryImpl(apiService = apiService)
    }

}