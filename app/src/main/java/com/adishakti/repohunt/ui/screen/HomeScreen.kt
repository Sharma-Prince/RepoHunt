package com.adishakti.repohunt.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adishakti.repohunt.R
import com.adishakti.repohunt.components.AppBar
import com.adishakti.repohunt.components.ErrorScreen
import com.adishakti.repohunt.components.NoData
import com.adishakti.repohunt.components.RepositoryItem
import com.adishakti.repohunt.components.SearchBar
import com.adishakti.repohunt.data.model.RepoItem
import com.adishakti.repohunt.ui.viewModels.HomeViewModel
import com.adishakti.repohunt.util.ResourceState


@Composable
fun HomeScreen(
    onRepoDetailClick: (RepoItem) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val lazyColumnListState = rememberLazyListState()
    val repositoriesState = viewModel.gitRepo.collectAsState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && viewModel.repoList.isNotEmpty() && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    val ctx = LocalContext.current
    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value) {
            Log.d("PrinceKumar", "pagination page = ${viewModel.page}")
            viewModel.fetchRepositories()
        }
    }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .width(48.dp)
        ) {
            AppBar(
                header = "Home", modifier = Modifier
            )
        }
        SearchBar(focusManager) { query ->
            viewModel.isStart = true
            viewModel.resetList()
            viewModel.query = query
            viewModel.fetchRepositories()
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            state = lazyColumnListState,
            modifier = Modifier.fillMaxWidth()
        ) {
            val list = viewModel.repoList
            items(list.toList()) { item ->
                RepositoryItem(repoItem = item, onRepoDetailClick = onRepoDetailClick)
                Divider()
            }

        }
        if (viewModel.isStart) {
            when (repositoriesState.value) {
                is ResourceState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ResourceState.PaginationLoading -> {

                }

                is ResourceState.Error -> {
                    ErrorScreen(
                        isInternetConnected = viewModel.isInternetConnected(),
                        isEmptyList = viewModel.repoList.isEmpty(),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (viewModel.isInternetConnected()) {
                            viewModel.fetchRepositories()
                        } else {
                            Toast.makeText(
                                ctx,
                                ctx.getString(R.string.connect_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                else -> {}
            }
        } else {
            NoData(
                message = "No data available",
                icon = Icons.Default.Info,
                iconContentDescription = "Info"
            )

        }


    }
}
