package com.lxy.responsivelayout.gesture

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lxy.responsivelayout.R
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 *
 * @Author：liuxy
 * @Date：2024/5/24 14:39
 * @Desc：
 *
 */

@Composable
fun GroupTitle(groupName: String, modifier: Modifier = Modifier) {
    Text(
        text = groupName,
        fontSize = 12.sp,
        modifier = modifier.padding(start = 12.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeEditScreenPage(
    mViewModel: HomeViewModel = viewModel()
) {
    val oftenMenus = mViewModel.oftenMenus.collectAsState().value
    val otherMenus = mViewModel.otherMenus.collectAsState().value
    val hideMenus = mViewModel.hideMenus.collectAsState().value
    var isSearchVisible by remember { mutableStateOf(false) }
    val height = remember {
        if (oftenMenus.size % 4 == 0) {
            oftenMenus.size / 4 * 100
        } else {
            (oftenMenus.size / 4 + 1) * 100
        }
    }
    val spaceHeight = 8
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopBar(
            onSearch = {isSearchVisible = true}
        ) },
    ) { innerPadding ->
        AnimatedVisibility(
            visible = isSearchVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ){
            val searchResult = mViewModel.searchResult.collectAsState().value
            HomeEditSearchDialog(
                onDismissRequest = {
                    mViewModel.clearSearchResult()
                    isSearchVisible = false },
                onSearch = {
                    mViewModel.searchMiniApp(it)
                },
                menuList = searchResult
            ){
                MenuItem(
                    item = it,
                    hiddenClick = {
                        if (it.isHidden) {
                            mViewModel.removeHideMenus(it)
                        } else {
                            mViewModel.addToHideMenus(it)
                        }
                    },
                    addToOften = {
                        mViewModel.addOftenMenus(it)
                    }) {
                    mViewModel.removeOftenMenus(it)
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .background(colorResource(id = R.color.bg))
                .padding(
                    top = innerPadding.calculateTopPadding() + 12.dp,
                    start = 12.dp,
                    end = 12.dp
                ),
            verticalArrangement = Arrangement.spacedBy(spaceHeight.dp),
        ) {

            item {
                GroupTitle(groupName = oftenMenus[0].name)
            }

            items(oftenMenus.size) {
                OftenMenuItem(
                    height = height,
                    item = oftenMenus[it],
                    index = it,
                    oftenMenus = oftenMenus
                )
            }

            items(otherMenus.size) { index ->
                val subList = otherMenus[index]
                otherMenus(
                    list = subList,
                    hiddenClick = {
                        val item = subList[it]
                        if (item.isHidden) {
                            mViewModel.removeHideMenus(it)
                        } else {
                            mViewModel.addToHideMenus(item)
                        }
                    },
                    addToOften = {
                        mViewModel.addOftenMenus(it)
                    }) {
                    mViewModel.removeOftenMenus(it)
                }
            }

            item {
                otherMenus(
                    list = hideMenus,
                    hiddenClick = {
                        mViewModel.removeHideMenus(it)
                    },
                    addToOften = {
                        mViewModel.addOftenMenus(it)
                    }) {
                    mViewModel.removeOftenMenus(it)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSearch : ()->Unit
) {


    val context = LocalContext.current
    val currentActivity = remember { context as? ComponentActivity }
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "工作台",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                currentActivity?.finish()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onSearch()
                }
            ) {
                Icon(Icons.Filled.Search, null)
            }
            IconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = {

                }
            ) {
                Icon(Icons.Outlined.Check, null)
            }
        },
    )
}


@Composable
fun otherMenus(
    modifier: Modifier = Modifier,
    list: List<MiniAppEntity>,
    hiddenClick: (Int) -> Unit,
    addToOften: (MiniAppEntity) -> Unit,
    removeToOften: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(6.dp, 12.dp)
    ) {
        Text(
            text = list.first().name, fontSize = 14.sp,
            modifier = Modifier.padding(start = 5.dp)
        )

        list.forEachIndexed { index, entity ->
            MenuItem(
                item = entity,
                hiddenClick = { hiddenClick(index) },
                addToOften = { addToOften(entity) },
                removeToOften = {
                    removeToOften(index)
                })
        }
    }
}

@Composable
fun OftenMenuItem(
    height: Int,
    item: MiniAppEntity,
    index: Int,
    oftenMenus: List<MiniAppEntity>
) {

    val density = LocalDensity.current.density
    var isDragging by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(IntOffset.Zero) }
    Row(
        modifier = Modifier
            .offset {
                if (isDragging) dragOffset else IntOffset.Zero
            }
            .padding(4.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        isDragging = true
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { down ->
                        if (isDragging) {
                            return@detectDragGestures
                        }
                        isDragging = true
                        dragOffset = IntOffset.Zero
                    },
                    onDrag = { change, dragAmount ->
                        dragOffset += IntOffset(0, dragAmount.y.roundToInt())
                    },
                    onDragEnd = {
                        isDragging = false
                        val newIndex = calculateNewIndex(
                            index,
                            dragOffset,
                            density,
                            height + 8,
                            oftenMenus
                        )
                        if (newIndex != index) {

                        }
                    }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.login_icon_person),
            modifier = Modifier
                .size(width = 48.dp, height = 48.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentDescription = ""
        )
        Text(
            text = item.name,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(6.dp)
        )
    }
}


@Composable
fun MenuItem(
    item: MiniAppEntity,
    hiddenClick: () -> Unit,
    addToOften: () -> Unit,
    removeToOften: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp, horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.login_icon_person),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp), text = item.name
        )

        val text = if (item.isOften) {
            "从常用中移除"
        } else {
            "添加至常用"
        }
        AssistChip(
            onClick = {
                if (item.isOften) {
                    removeToOften()
                } else {
                    addToOften()
                }
            },
            label = { Text(text) },
            leadingIcon = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Localized description",
                    Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )

        val icon = if (item.isHidden) {
            Icons.Filled.FavoriteBorder
        } else {
            Icons.Filled.FavoriteBorder
        }
        Icon(
            icon,
            contentDescription = "Localized description",
            Modifier
                .size(AssistChipDefaults.IconSize)
                .padding(start = 5.dp)
                .clickable {
                    hiddenClick()
                }
        )
    }
}

@Composable
private fun Model(){

}

private fun calculateNewIndex(
    draggedItemIndex: Int,
    dragOffset: IntOffset,
    density: Float,
    height: Int,
    list: List<MiniAppEntity>
): Int {
    val heightPx = height * density
    val moveItemNums = ((abs(dragOffset.y) + (heightPx / 2)) / heightPx).toInt()
    val newIndex = if (dragOffset.y < 0) {
        draggedItemIndex - moveItemNums
    } else {
        draggedItemIndex + moveItemNums
    }
    return newIndex.coerceIn(0, list.size - 1)
}
