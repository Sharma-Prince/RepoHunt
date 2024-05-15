package com.adishakti.repohunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adishakti.repohunt.ui.RepoHuntApp
import com.adishakti.repohunt.ui.theme.RepoHuntTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepoHuntTheme {
                RepoHuntApp()
            }
        }
    }
}
