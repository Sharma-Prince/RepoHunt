package com.adishakti.repohunt.ui.navigation

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.adishakti.repohunt.data.model.RepoItem
import com.adishakti.repohunt.ui.screen.RepoDetailsScreen
import com.adishakti.repohunt.util.convertDataClassToJson
import com.adishakti.repohunt.util.convertJsonToDataClass


const val repositoryScreenNavigationRoute = "repository_screen_route"
const val contributorRoutePattern = "contributor_view_graph"
const val aboutRepoItemParams = "aboutRepoItem"

fun NavController.navigateToRepositoryDetail(
    navOptions: NavOptions? = null,
    repoItem: RepoItem
) {
    val json = convertDataClassToJson<RepoItem>(repoItem)
    this.navigate("$repositoryScreenNavigationRoute/$json", navOptions)
}

fun NavGraphBuilder.repositoryScreen(
    onBackClick: () -> Unit,
    onUrlClick: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = contributorRoutePattern,
        startDestination = repositoryScreenNavigationRoute
    ) {
        composable(
            route = "$repositoryScreenNavigationRoute/{$aboutRepoItemParams}",
            arguments = listOf(navArgument(aboutRepoItemParams) { type = NavType.StringType })
        ) {
            val argument = it.arguments?.getString(aboutRepoItemParams)
            val nameArgument = argument?.let { it1 -> convertJsonToDataClass<RepoItem>(it1) }
            val context: Context = LocalContext.current
            RepoDetailsScreen(
                onBackClick,
                onUrlClick,
                nameArgument = nameArgument
            )
        }
        nestedGraphs()
    }
}

