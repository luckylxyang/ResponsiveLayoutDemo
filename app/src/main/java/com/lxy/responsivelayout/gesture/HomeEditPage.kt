package com.lxy.responsivelayout.gesture

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

val oftenMenus = listOf(
    MiniAppEntity("App1"),
    MiniAppEntity("App2"),
    MiniAppEntity("App3"),
    MiniAppEntity("App4"),
    MiniAppEntity("App5"),
    MiniAppEntity("App6"),
    MiniAppEntity("App7")
)

val otherMenus = listOf(
    MiniAppEntity("App1"),
    MiniAppEntity("App2"),
    MiniAppEntity("App3"),
    MiniAppEntity("App4"),
    MiniAppEntity("App5"),
    MiniAppEntity("App6"),
    MiniAppEntity("App7")
)

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
//    viewModel: HomeViewModel = viewModel()
) {
//    val oftenMenus = viewModel.oftenMenus.collectAsState()
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
        topBar = { TopBar() },
    ) { innerPadding ->
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
                OftenMenuItem(height = height, item = oftenMenus[it], index = it)
            }

            item {
                otherMenus()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {


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
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding()
    ) {
        Text(text = otherMenus.first().name)

        otherMenus.forEach {
            MenuItem(item = it, hiddenClick = { /*TODO*/ }, addToOften = { /*TODO*/ }) {

            }
        }
    }
}

@Composable
fun OftenMenuItem(
    height: Int,
    item: MiniAppEntity,
    index: Int
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
            painter = painterResource(id = R.mipmap.ic_launcher),
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
    Row {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.mipmap.ic_launcher),
            contentDescription = null
        )
        Text(text = item.name)

        AssistChip(
            onClick = {
                if (item.isOften) {
                    removeToOften()
                } else {
                    addToOften()
                }
            },
            label = { Text("Assist chip") },
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
                .clickable {
                    hiddenClick()
                }
        )
    }
}

fun calculateNewIndex(
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
