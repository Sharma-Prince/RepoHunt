package com.adishakti.repohunt.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.adishakti.repohunt.data.model.RepoItem


sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    companion object {
        const val ARG_REPO_ID = "repoId"
        const val ARG_OWNER_ID = "ownerId"
        const val ARG_REPO_ITEM = "repoItem"
    }

    data object Home : Screen("home")

    data object RepoDetails : Screen(
        route = "repoDetails/{$ARG_OWNER_ID}/{$ARG_REPO_ID}",
        navArguments = listOf(
            navArgument(ARG_REPO_ID) { type = NavType.StringType },
            navArgument(ARG_OWNER_ID) { type = NavType.StringType },
            navArgument(ARG_REPO_ITEM) { type = NavType.ParcelableType(RepoItem::class.java) }
        )
    ) {
        fun createRoute(fullName: String) = "repoDetails/$fullName"
    }

}