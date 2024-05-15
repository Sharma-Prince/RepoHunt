package com.adishakti.repohunt.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adishakti.repohunt.data.model.Contributor
import com.adishakti.repohunt.data.repository.GitRepositoryRepository
import com.adishakti.repohunt.util.NetworkUtils
import com.adishakti.repohunt.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val repository: GitRepositoryRepository,
    private val networkUtils: NetworkUtils
) : ViewModel() {
    private val _repositoryDetails =
        MutableStateFlow<ResourceState<List<Contributor>>>(ResourceState.Loading())
    val repositoryDetails: MutableStateFlow<ResourceState<List<Contributor>>> = _repositoryDetails

    fun getRepositoryDetails(repoId: String, ownerId: String) {
        viewModelScope.launch {
            val repoDetails =
                repository.getRepositoryDetails(repoName = repoId, ownerName = ownerId)
            repoDetails.collectLatest {
                _repositoryDetails.value = it
            }
        }
    }

    fun isInternetConnected(): Boolean {
        return networkUtils.isNetworkConnected()
    }
}
