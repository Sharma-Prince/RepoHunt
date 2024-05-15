package com.adishakti.repohunt.hilt

import com.adishakti.repohunt.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceProviderModule {

    @Provides
    @Singleton
    fun providesHomeService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}