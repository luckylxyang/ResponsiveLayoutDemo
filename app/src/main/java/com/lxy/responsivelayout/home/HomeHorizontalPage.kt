package com.lxy.responsivelayout.home

import android.content.Intent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.feedback.FeedbackDetailActivity
import com.lxy.responsivelayout.gesture.DraggableVerticalGrid
import com.lxy.responsivelayout.gesture.HomeViewModel
import com.lxy.responsivelayout.gesture.MiniAppEntity
import com.lxy.responsivelayout.gesture.otherMenu
import kotlinx.coroutines.launch

/**
 *
 * @Author：liuxy
 * @Date：2024/6/7 15:13
 * @Desc：
 *
 */

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeHorizontalScreenPage(
    mViewModel: HomeViewModel = viewModel()
) {


    val all = remember { mViewModel.otherMenus.value }
    Scaffold {padding->
        Column(modifier = Modifier
            .background(Color(0xFFEEEFF2))
            .padding(padding)
            .fillMaxSize()) {
            val pagerState = rememberPagerState(pageCount = {
                all.size
            })

            HorizontalIndex(pagerState = pagerState) {index->
                all[index].firstOrNull()?.name?:""
            }
            ViewPager(state = pagerState, all){from, to->
                mViewModel.onMove(from, to, pagerState.currentPage)
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalIndex(
    pagerState: PagerState,
    getTitleText: (Int) -> String
) {
    val scope = rememberCoroutineScope()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color(0xFFEEEFF2))
            .padding(8.dp)
            .wrapContentWidth()
    ) {
        repeat(pagerState.pageCount) { index ->
//            content(index)
            var textWidth by remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
            ) {
                Text(
                    text = getTitleText(index),
                    fontSize = 14.sp,
                    color = if (pagerState.currentPage == index) Color(0xFF323233) else Color(0xFF969799),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .onGloballyPositioned { coordinates ->
                            // 获取文本宽度
                            textWidth = with(density) { coordinates.size.width.toDp() }
                        }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(textWidth)
                        .background(if (index == pagerState.currentPage) Color(0xFF426EF5) else Color.Transparent)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ViewPager(
    state: PagerState,
    all: List<List<MiniAppEntity>>,
    onMove: (Int, Int) -> Unit,
) {

    HorizontalPager(
        state = state,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        MenusLayout(list = all[page], onMove = onMove)

    }
}

/**
 * 每个分类下的微应用
 */
@Composable
private fun MenusLayout(
    modifier: Modifier = Modifier,
    list: List<MiniAppEntity>,
    onMove: (Int, Int) -> Unit,
) {

    var list by remember { mutableStateOf(list) }

    DraggableVerticalGrid(
        list,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xEEEFF2)),
        itemKey = { _, item ->
            "${item.id}"
        },
        onMove = { fromIndex, toIndex ->
            list = list.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
//            onMove(fromIndex, toIndex)
        },
        columns = GridCells.Adaptive(196.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) { item, isDragging ->
        MenuItem(item)

    }

}

/**
 * 横屏的菜单子项
 */
@Composable
private fun MenuItem(
    item: MiniAppEntity,
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .clickable {
                context.startActivity(Intent(context, FeedbackDetailActivity::class.java))

            }
            .background(Color.White, RoundedCornerShape(2.dp))
            .padding(12.dp, 10.dp)
    ) {
        Row {
            Image(
                painterResource(id = R.drawable.login_icon_person),
                null,
                modifier = Modifier
                    .size(42.dp)
                    .shadow(elevation = 2.dp, shape = MaterialTheme.shapes.small)
            )

            Column(
                modifier = Modifier.padding(start = 6.dp)
            ) {
                Text(text = item.name, style = MaterialTheme.typography.labelLarge)

                Text(text = item.name, style = MaterialTheme.typography.labelMedium)
            }
        }

        Icon(
            Icons.Outlined.MoreVert,
            null,
            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .clickable {

                }
        )

    }
}