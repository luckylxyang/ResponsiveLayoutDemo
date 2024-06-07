package com.lxy.responsivelayout.home

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.gesture.DraggableVerticalGrid
import com.lxy.responsivelayout.gesture.HomeViewModel
import com.lxy.responsivelayout.gesture.MiniAppEntity
import kotlinx.coroutines.launch

/**
 *
 * @Author：liuxy
 * @Date：2024/6/7 15:13
 * @Desc：
 *
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeHorizontalScreenPage(
    mViewModel: HomeViewModel = viewModel()
) {

    val all = remember { mViewModel.otherMenus.value }
    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount = {
            all.size
        })
        HorizontalIndex(pagerState = pagerState) {
            val color = if (pagerState.currentPage == it) Color.Blue else Color.DarkGray
            Column {
                Text(
                    modifier = Modifier
                        .padding(2.dp),
                    text = "${all[it].firstOrNull()?.name}",
                    color = color,
                    fontSize = TextUnit(14f, TextUnitType.Sp)
                )
                if (pagerState.currentPage == it){
                    val thickness = 2.dp
                    Canvas(
                        Modifier
                            .wrapContentWidth()
                            .height(thickness)) {
                        drawLine(
                            color = color,
                            strokeWidth = thickness.toPx(),
                            start = Offset(0f, thickness.toPx() / 2),
                            end = Offset(size.width, thickness.toPx() / 2),
                        )
                    }
                }
            }

        }
        ViewPager(state = pagerState, all){from, to->

        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalIndex(
    pagerState: PagerState,
    content: @Composable (Int) -> Unit
) {
    Row(
        Modifier
//            .wrapContentHeight()
//            .fillMaxWidth()
//            .padding(bottom = 8.dp),
    ) {
        repeat(pagerState.pageCount) { iteration ->
            content(iteration)
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
//    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 160.dp)) {
//        items(list.size) {
//            MenuItem(list[it])
//        }
//    }
    DraggableVerticalGrid(
        list,
        itemKey = { index, item ->
            "${item.id}"
        },
        onMove = { fromIndex, toIndex ->
//            list = list.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
            onMove(fromIndex, toIndex)
        },
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
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
    Box(
        modifier = Modifier
            .clickable {
//                onItemClick()
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