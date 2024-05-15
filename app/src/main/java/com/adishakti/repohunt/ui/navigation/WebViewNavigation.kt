package com.adishakti.repohunt.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.adishakti.repohunt.ui.screen.WebViewScreen
import com.adishakti.repohunt.util.convertDataClassToJson
import com.adishakti.repohunt.util.convertJsonToDataClass

const val webViewScreenNavigationRoute = "photo_view_screen_route"
const val UlrArg = "url_arg"


fun NavController.navigateToWebView(navOptions: NavOptions? = null, url: String) {
    val json = convertDataClassToJson<String>(url)
    this.navigate("$webViewScreenNavigationRoute/$json", navOptions)
}

fun NavGraphBuilder.webViewScreen(onBackClick: () -> Unit) {
    composable(
        route = "$webViewScreenNavigationRoute/{$UlrArg}",
        arguments = listOf(navArgument(UlrArg) { type = NavType.StringType })
    ) {

        val argument = it.arguments?.getString(UlrArg)
        val nameArgument = argument?.let { it1 -> convertJsonToDataClass<String>(it1) }

        if (nameArgument != null) {
            WebViewScreen(nameArgument)
        }
    }
}