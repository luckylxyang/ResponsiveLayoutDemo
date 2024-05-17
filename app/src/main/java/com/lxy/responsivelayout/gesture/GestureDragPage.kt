package com.lxy.responsivelayout.gesture

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lxy.responsivelayout.R

/**
 *
 * @Author：liuxy
 * @Date：2024/5/15 16:14
 * @Desc：
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestureDragPage() {

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
            GestureLock(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun GestureLock(
    modifier: Modifier = Modifier,
    drawStart: (() -> Unit)? = null,
    drawEnd: ((List<Int>) -> Unit)? = null,
    selectColor : Color = Color.Blue,
    defaultColor : Color = Color.Gray,
    circleRadius: Int = 30
) {
    var path by remember { mutableStateOf(Path()) }
    val points = remember { mutableStateListOf<Offset>() }
    val selectedCircles = remember { mutableStateListOf<Int>() }
    val circleRadiusPx = with(LocalDensity.current) { circleRadius.dp.toPx() }
    val circleMargin = with(LocalDensity.current) { 40.dp.toPx() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val index = detectCircle(offset, circleRadiusPx, circleMargin)
                        if (index != -1 && !selectedCircles.contains(index)) {
                            selectedCircles.add(index)
                            points.add(getCircleCenter(index, circleRadiusPx, circleMargin))
                            path = updatePath(points)
                        }
                        if (drawStart != null){
                            drawStart()
                        }
                    },
                    onDrag = { change, _ ->
                        val index = detectCircle(change.position, circleRadiusPx, circleMargin)
                        if (index != -1 && !selectedCircles.contains(index)) {
                            selectedCircles.add(index)
                            points.add(getCircleCenter(index, circleRadiusPx, circleMargin))
                        }
                        if (points.isNotEmpty()) {
                            path = updatePath(points)
                            path.lineTo(change.position.x, change.position.y)
                        }
                    },
                    onDragEnd = {

                        if (drawEnd != null && points.isNotEmpty()){
                            drawEnd(selectedCircles)
                        }
                        points.clear()
                        selectedCircles.clear()
                        path = Path()
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
            for (i in 0..8) {
                val center = getCircleCenter(i, circleRadiusPx, circleMargin)
                val isSelect = selectedCircles.contains(i)
                drawCircle(
                    color = if (!isSelect) defaultColor else selectColor,
                    radius = circleRadiusPx,
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )
            }

            drawPath(
                path = path,
                color = selectColor,
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

private fun detectCircle(offset: Offset, circleRadius: Float, circleMargin: Float): Int {
    for (i in 0..8) {
        val center = getCircleCenter(i, circleRadius, circleMargin)
        if (offset.dst(center) <= circleRadius) {
            return i
        }
    }
    return -1
}

private fun getCircleCenter(index: Int, circleRadius: Float, circleMargin: Float): Offset {
    val row = index / 3
    val col = index % 3
    return Offset(
        x = circleMargin * (col + 1) + circleRadius * (2 * col + 1),
        y = circleMargin * (row + 1) + circleRadius * (2 * row + 1)
    )
}

private fun Offset.dst(other: Offset): Float {
    return kotlin.math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
}

private fun updatePath(points: List<Offset>): Path {
    val path = Path()
    if (points.isNotEmpty()) {
        path.moveTo(points[0].x, points[0].y)
        points.forEach { point ->
            path.lineTo(point.x, point.y)
        }
    }
    return path
}
