package com.lxy.responsivelayout

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

/**
 *
 * @Author：liuxy
 * @Date：2024/4/24 16:09
 * @Desc：
 *
 */


val list = listOf("index1", "index2", "index3", "index4", "index5", "index6", "index7")
val itemHeight = 100

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


    LazyColumn(
        modifier = Modifier
            .background(colorResource(id = R.color.white))
            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {


        items(itemsList.size) {
            val item = itemsList[it]
            var offsetX by remember { mutableStateOf(0f) }
            var offsetY by remember { mutableStateOf(0f) }
            var isDragging by remember { mutableStateOf(false) }
            var dragOffset by remember { mutableStateOf(IntOffset.Zero) }
            Row(
                modifier = Modifier
                    .offset {
                        if (isDragging) dragOffset else IntOffset.Zero
//                        IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
                    }
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(itemHeight.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { down ->
                                isDragging = true
                                dragOffset = IntOffset.Zero
                            },
                            onDrag = { change, dragAmount ->
//                                change.consume()
                                dragOffset += IntOffset(0, dragAmount.y.roundToInt())
                            },
                            onDragEnd = {
                                isDragging = false
                                Log.d("Drag", "移动的项是$it，content = $item")
                                val newIndex = calculateNewIndex(it, dragOffset)
                                Log.d("Drag", "移动后的位置是$newIndex，content = $item")
                                if (newIndex != it) {
                                    itemsList = moveItem(itemsList, it, newIndex)
                                }
                            }
                        )
                    },
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
        }

    }
}
fun calculateNewIndex(draggedItemIndex: Int, dragOffset: IntOffset): Int {
    val draggedItemY = draggedItemIndex * itemHeight // 假设每个子项的高度相等
    val draggedItemCenterY = draggedItemY + (itemHeight / 2)
    val newIndex = (draggedItemCenterY + dragOffset.y) / itemHeight
    return newIndex.coerceIn(0, list.size - 1)
}

fun moveItem(list: List<String>, oldIndex: Int, newIndex:Int) : List<String>{
    val mutableList = list.toMutableList()
    mutableList.add(newIndex, mutableList.removeAt(oldIndex))

    return mutableList.toList()
}