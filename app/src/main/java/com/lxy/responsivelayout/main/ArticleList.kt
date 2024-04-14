package com.lxy.responsivelayout.main

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lxy.responsivelayout.entity.Article
import com.lxy.responsivelayout.nav.RLDestinations

@Composable
fun article(
    navController: NavHostController,
    isExtend: Boolean,
    messages: List<Article>,
) {
    val viewModel : MainViewModel = viewModel()
    Row {
        ArticleList(messages = messages, viewModel = viewModel)
        Spacer(modifier = Modifier.padding(10.dp))
        if (isExtend) {

//            ArticleDetail(viewModel)
        }
    }
}


@Composable
fun ArticleList(
    messages: List<Article>,
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel
) {
    Column {
        messages.forEach { message ->
            ArticleItem(message, navController, viewModel)
        }
    }
}


@Composable
fun ArticleItem(
    article: Article,
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel
) {
    Column(modifier = Modifier.clickable {
        Log.d("TAG", "ArticleItem: ${article.url}")
        viewModel.setSelectItem(article = article)
    }) {
        Text(text = article.title, modifier = Modifier.padding(5.dp))
        Text(text = article.author, modifier = Modifier.padding(5.dp))
    }
}

@Preview
@Composable
fun item() {
    val list = arrayListOf<Article>(
        Article(
            "我是一个标题",
            "https://developer.android.google.cn/guide/topics/large-screens/support-different-screen-sizes?hl=zh-cn",
            "test"
        ),
        Article(
            "我是二个标题",
            "https://developer.android.google.cn/develop/ui/compose/layouts/adaptive?hl=zh-cn",
            "test2"
        ),
    )
}