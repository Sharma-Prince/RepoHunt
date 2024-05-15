package com.adishakti.repohunt.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.adishakti.repohunt.data.model.RepoItem
import com.adishakti.repohunt.ui.screen.HomeScreen

const val homeScreenRoutePattern = "top_screen_detail_graph"
const val homeScreenNavigationRoute = "home_screen_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeScreenRoutePattern, navOptions)
}

fun NavGraphBuilder.homeScreen(
    repoOnClick: (RepoItem) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = homeScreenRoutePattern,
        startDestination = homeScreenNavigationRoute
    ) {
        composable(
            route = homeScreenNavigationRoute,
        ) {
            HomeScreen(repoOnClick)
        }
        nestedGraphs()
    }
}