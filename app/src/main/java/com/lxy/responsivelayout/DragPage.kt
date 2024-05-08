package com.lxy.responsivelayout

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 *
 * @Author：liuxy
 * @Date：2024/4/24 16:09
 * @Desc：
 *
 */


val list = listOf("index0", "index1", "index2", "index3", "index4", "index5", "index6", "index7")
val itemHeight = 40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.drag))
                },
                actions = {
                    IconButton(
                        onClick = { } //do something
                    ) {
                        Icon(Icons.Filled.Search, null)
                    }
                    Text(text = "编辑")
                }

            )
        },
    ) { innerPadding ->

        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            DragPage(list = list)
        }
    }
}

@Composable
fun DragPage(
    list: List<String>
) {

    var itemsList by remember { mutableStateOf(list) }
    val heights = remember { mutableStateListOf<Int>() }
    val density = LocalDensity.current.density

    LazyColumn(
        modifier = Modifier
            .background(colorResource(id = R.color.white))
            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        items(itemsList.size) {
            val item = itemsList[it]
            var isDragging by remember { mutableStateOf(false) }
            var dragOffset by remember { mutableStateOf(IntOffset.Zero) }
            Column(
                modifier = Modifier
                    .offset {
                        if (isDragging) dragOffset else IntOffset.Zero
                    }
                    .height(itemHeight.dp)
                    .padding(4.dp)
                    .fillMaxWidth()
                    .onGloballyPositioned {

                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { down ->
                                isDragging = true
                                dragOffset = IntOffset.Zero
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffset += IntOffset(0, dragAmount.y.roundToInt())
                            },
                            onDragEnd = {
                                isDragging = false
                                val newIndex = calculateNewIndex(it, dragOffset, density)
                                if (newIndex != it) {
                                    itemsList = moveItem(itemsList, it, newIndex)
                                }
                            }
                        )
                    },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.login_icon_person),
                        modifier = Modifier
                            .size(width = 48.dp, height = 48.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        contentDescription = ""
                    )
                    Text(
                        text = "$item",
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(6.dp)
                    )
                }
                HorizontalDivider()
            }

        }

    }
}


fun calculateNewIndex(draggedItemIndex: Int, dragOffset: IntOffset, density: Float): Int {
    val heightPx = itemHeight * density
    val moveItemNums = ((abs(dragOffset.y) + (heightPx / 2)) / heightPx).toInt()
    val newIndex = if (dragOffset.y < 0){
        draggedItemIndex - moveItemNums
    } else {
        draggedItemIndex + moveItemNums
    }
    return newIndex.coerceIn(0, list.size - 1)
}

fun moveItem(list: List<String>, oldIndex: Int, newIndex: Int): List<String> {
    val mutableList = list.toMutableList()
    mutableList.add(newIndex, mutableList.removeAt(oldIndex))

    return mutableList.toList()
}

@Composable
fun VerticalReorderList() {
    val data = remember { mutableStateOf(List(100) { "Item $it" }) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(data.value.size, { it }) { item ->
            ReorderableItem(state, key = item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "$item")
                }
            }
        }
    }
}
