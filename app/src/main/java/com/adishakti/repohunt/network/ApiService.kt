package com.adishakti.repohunt.network

import com.adishakti.repohunt.data.model.Contributor
import com.adishakti.repohunt.data.model.GitRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): Response<GitRepository>

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<List<Contributor>>
}