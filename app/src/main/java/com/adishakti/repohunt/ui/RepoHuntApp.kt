package com.adishakti.repohunt.ui


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.adishakti.repohunt.ui.navigation.homeScreen
import com.adishakti.repohunt.ui.navigation.homeScreenRoutePattern
import com.adishakti.repohunt.ui.navigation.navigateToRepositoryDetail
import com.adishakti.repohunt.ui.navigation.navigateToWebView
import com.adishakti.repohunt.ui.navigation.repositoryScreen
import com.adishakti.repohunt.ui.navigation.webViewScreen


@Composable
fun RepoHuntApp(
    startDestination: String = homeScreenRoutePattern,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = homeScreenRoutePattern
    ) {
        homeScreen(
            repoOnClick = {
                navController.navigateToRepositoryDetail(repoItem = it)
            },
            nestedGraphs = {
                repositoryScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onUrlClick = {
                        navController.navigateToWebView(url = it)
                    },
                    nestedGraphs = {
                        webViewScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                )
            }
        )
    }
}

