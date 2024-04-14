package com.lxy.responsivelayout.main

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.lxy.responsivelayout.entity.Article

@Composable
fun ArticleDetail(article: Article , viewModel: MainViewModel){


    Column(modifier = Modifier.fillMaxHeight()) {
        AndroidView(factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }
        }, modifier = Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun ArticleDetailPreview(){
    val list = Article("我是一个标题", "https://developer.android.google.cn/guide/topics/large-screens/support-different-screen-sizes?hl=zh-cn", "test")
//    ArticleDetail(list)
}