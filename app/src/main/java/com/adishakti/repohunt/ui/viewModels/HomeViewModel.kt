package com.adishakti.repohunt.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adishakti.repohunt.data.model.GitRepository
import com.adishakti.repohunt.data.model.RepoItem
import com.adishakti.repohunt.data.model.toCombinedRepo
import com.adishakti.repohunt.data.model.toRepoItem
import com.adishakti.repohunt.data.repository.GitRepositoryRepository
import com.adishakti.repohunt.data.repository.RepoDaoRepository
import com.adishakti.repohunt.util.NetworkUtils
import com.adishakti.repohunt.util.ResourceState
import com.adishakti.repohunt.util.STORE_MAX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class HomeViewModel @Inject internal constructor(
    private val repository: GitRepositoryRepository,
    private val daoRepository: RepoDaoRepository,
    private val networkUtils: NetworkUtils,
) : ViewModel() {

    val repoList = mutableStateListOf<RepoItem>()
    var page by mutableIntStateOf(1)
    var query by mutableStateOf("")
    var isLastPage by mutableStateOf(false)
    var canPaginate by mutableStateOf(false)
    var isStart by mutableStateOf(false)

    private val _gitRepo = MutableStateFlow<ResourceState<GitRepository>>(ResourceState.Loading())
    val gitRepo: MutableStateFlow<ResourceState<GitRepository>> = _gitRepo


    private fun getAllLocal() {
        daoRepository.getAllItem().observeForever { newList ->
            repoList.addAll(newList.map { it.toRepoItem() })
        }
    }

    init {
        getAllLocal()
    }

    fun getLocalSize(): Int {
        return daoRepository.getRepoTableSize()
    }

    fun fetchRepositories() {
        viewModelScope.launch {
            if (page == 1 || (page != 1 && canPaginate)) {
                val repo = repository.searchRepositories(query, page)
                repo.collectLatest { it ->
                    _gitRepo.value = it
                    canPaginate = true
                    when (it) {
                        is ResourceState.Success -> {
                            val data = it.data
                            isLastPage = true
                            if (repoList.isEmpty() || page == 1) {
                                repoList.clear()
                                daoRepository.deleteAllItems()
                            }
                            page += 1
                            data.items?.filterNotNull()?.let {
                                repoList.addAll(it)
                                val localDataSize = daoRepository.getRepoTableSize()
                                val toTake = min(STORE_MAX, STORE_MAX - localDataSize)
                                if (localDataSize <= STORE_MAX) {
                                    daoRepository.insertAllItems(it.take(toTake).map { repoItem ->
                                        repoItem.toCombinedRepo()
                                    })
                                }
                            }
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    fun resetList() {
        isLastPage = false
        canPaginate = false
        repoList.clear()
        page = 1
    }

    fun isInternetConnected(): Boolean {
        return networkUtils.isNetworkConnected()
    }
}


