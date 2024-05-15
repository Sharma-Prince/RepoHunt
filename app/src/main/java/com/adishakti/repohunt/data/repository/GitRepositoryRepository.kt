package com.adishakti.repohunt.data.repository


import com.adishakti.repohunt.data.model.Contributor
import com.adishakti.repohunt.data.model.GitRepository
import com.adishakti.repohunt.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface GitRepositoryRepository {
    suspend fun searchRepositories(query: String, page: Int): Flow<ResourceState<GitRepository>>
    suspend fun getRepositoryDetails(
        repoName: String,
        ownerName: String
    ): Flow<ResourceState<List<Contributor>>>
}