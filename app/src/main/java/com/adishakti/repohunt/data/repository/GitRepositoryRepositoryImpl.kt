package com.adishakti.repohunt.data.repository

import com.adishakti.repohunt.data.model.Contributor
import com.adishakti.repohunt.data.model.GitRepository
import com.adishakti.repohunt.network.ApiService
import com.adishakti.repohunt.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GitRepositoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GitRepositoryRepository {
    override suspend fun searchRepositories(
        query: String,
        page: Int,
    ): Flow<ResourceState<GitRepository>> {
        return flow {
            val response = apiService.searchRepositories(query, page)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(
                    ResourceState.Error(
                        response.errorBody() ?: "Something went wrong."
                    )
                )
            }
        }.catch {
            emit(ResourceState.Error(it.localizedMessage ?: "Unexpected error in flow."))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getRepositoryDetails(
        repoName: String,
        ownerName: String,
    ): Flow<ResourceState<List<Contributor>>> {
        return flow {
            val response = apiService.getRepositoryDetails(owner = ownerName, repo = repoName)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(
                    ResourceState.Error(
                        response.errorBody() ?: "Something went wrong."
                    )
                )
            }
        }.catch {
            emit(ResourceState.Error(it.localizedMessage ?: "Unexpected error in flow."))
        }.flowOn(Dispatchers.IO)
    }

}