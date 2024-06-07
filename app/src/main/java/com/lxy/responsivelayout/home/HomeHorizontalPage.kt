package com.lxy.responsivelayout.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 *
 * @Author：liuxy
 * @Date：2024/6/7 15:13
 * @Desc：
 *
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeHorizontalScreenPage(){

    Box(modifier = Modifier.fillMaxSize()){
        val pagerState = rememberPagerState(pageCount = {
            4
        })
        ViewPager(state = pagerState)
        HorizontalIndex(pagerState = pagerState)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalIndex(
    pagerState : PagerState
){
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Text(
                modifier = Modifier
                    .padding(2.dp)
                    .background(color)
                    .size(16.dp),
                text = "$iteration"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ViewPager(
    state : PagerState
){

    HorizontalPager(
        state = state,
        modifier = Modifier.fillMaxSize()
    ) { page ->


    }
}

/**
 * 每个分类下的微应用
 */
@Composable
private fun MenusLayout(
    modifier: Modifier = Modifier,
    list : List<String>,

){
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 160.dp)){
        items(list.size){
            MenuItem(list[it])
        }
    }
}

/**
 * 横屏的菜单子项
 */
@Composable
private fun MenuItem(
    item: String
){
    Card {
        Text(text = item)
    }
}