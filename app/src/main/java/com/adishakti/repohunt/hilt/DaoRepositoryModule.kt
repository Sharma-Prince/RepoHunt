package com.adishakti.repohunt.hilt

import com.adishakti.repohunt.dao.RepoDataAccessObject
import com.adishakti.repohunt.data.repository.RepoDaoRepository
import com.adishakti.repohunt.data.repository.RepoDaoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
class DaoRepositoryModule {


    @Provides
    fun objectForCartRepositoryInterface(@Singleton cartDataAccessObject: RepoDataAccessObject): RepoDaoRepository {
        return RepoDaoRepositoryImpl(cartDataAccessObject)
    }
}