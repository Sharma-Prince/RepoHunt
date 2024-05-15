package com.adishakti.repohunt.ui.screen

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(
    url: String,
    webViewClient: WebViewClient = WebViewClient(),
    loadingContent: @Composable () -> Unit = { CircularProgressIndicator() }
) {
    var isLoading by remember { mutableStateOf(true) }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
                this.webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        isLoading = newProgress != 100
                    }
                }
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            loadingContent()
        }
    }
}
