package com.lxy.responsivelayout.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lxy.responsivelayout.entity.Article


@Composable
fun ArticleList(messages: List<Article>){


    Column {
        messages.forEach { message ->
            ArticleItem(message)
        }
    }
}


@Composable
fun ArticleItem(article: Article){
    Column(modifier = Modifier.clickable {
//        ArticleDetail(article = article)
    }) {
        Text(text = article.title, modifier = Modifier.padding(5.dp))
        Text(text = article.author, modifier = Modifier.padding(5.dp))
    }
}

@Preview
@Composable
fun item(){
    val list = arrayListOf<Article>(
        Article("我是一个标题", "https://developer.android.google.cn/guide/topics/large-screens/support-different-screen-sizes?hl=zh-cn", "test"),
        Article("我是二个标题", "https://developer.android.google.cn/develop/ui/compose/layouts/adaptive?hl=zh-cn", "test2"),
    )
    ArticleList(list)
}