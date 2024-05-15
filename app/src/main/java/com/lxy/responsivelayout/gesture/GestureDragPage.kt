package com.lxy.responsivelayout.gesture

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
            GestureLock()
        }
    }
}

@Composable
fun GestureLock(
    drawEnd: ((List<Int>) -> Unit?)? = null,
    selectColor : Color = Color.Blue,
    defaultColor : Color = Color.Gray
) {
    var path by remember { mutableStateOf(Path()) }
    val points = remember { mutableStateListOf<Offset>() }
    val selectedCircles = remember { mutableStateListOf<Int>() }
    val circleRadius = with(LocalDensity.current) { 30.dp.toPx() }
    val circleMargin = with(LocalDensity.current) { 40.dp.toPx() }
    var verificationResult by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    // 定义正确的路径顺序
    val correctPattern = listOf(0, 1, 2, 5, 8)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val index = detectCircle(offset, circleRadius, circleMargin)
                        if (index != -1 && !selectedCircles.contains(index)) {
                            selectedCircles.add(index)
                            points.add(getCircleCenter(index, circleRadius, circleMargin))
                            path = updatePath(points)
                        }
                    },
                    onDrag = { change, _ ->
                        val index = detectCircle(change.position, circleRadius, circleMargin)
                        if (index != -1 && !selectedCircles.contains(index)) {
                            selectedCircles.add(index)
                            points.add(getCircleCenter(index, circleRadius, circleMargin))
                        }
                        if (points.isNotEmpty()) {
                            path = updatePath(points)
                            path.lineTo(change.position.x, change.position.y)
                        }
                    },
                    onDragEnd = {
                        verificationResult = validatePattern(selectedCircles, correctPattern)
                        text = if (!verificationResult){
                            "Pattern does not match"
                        } else {
                            "Pattern does match"
                        }
                        if (drawEnd != null){
                            drawEnd(selectedCircles)
                        }
                        points.clear()
                        selectedCircles.clear()
                        path = Path() // Reset the path
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            for (i in 0..8) {
                val center = getCircleCenter(i, circleRadius, circleMargin)
                val isSelect = selectedCircles.contains(i)
                drawCircle(
                    color = if (!isSelect) defaultColor else selectColor,
                    radius = circleRadius,
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


        Text(
            text = text,
            color = Color.Red,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }
}

fun detectCircle(offset: Offset, circleRadius: Float, circleMargin: Float): Int {
    for (i in 0..8) {
        val center = getCircleCenter(i, circleRadius, circleMargin)
        if (offset.dst(center) <= circleRadius) {
            return i
        }
    }
    return -1
}

fun getCircleCenter(index: Int, circleRadius: Float, circleMargin: Float): Offset {
    val row = index / 3
    val col = index % 3
    return Offset(
        x = circleMargin * (col + 1) + circleRadius * (2 * col + 1),
        y = circleMargin * (row + 1) + circleRadius * (2 * row + 1)
    )
}

fun Offset.dst(other: Offset): Float {
    return kotlin.math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
}

fun updatePath(points: List<Offset>): Path {
    val path = Path()
    if (points.isNotEmpty()) {
        path.moveTo(points[0].x, points[0].y)
        points.forEach { point ->
            path.lineTo(point.x, point.y)
        }
    }
    return path
}

fun validatePattern(selectedCircles: List<Int>, correctPattern: List<Int>): Boolean {
    if (selectedCircles.size != correctPattern.size) {
        return false
    }
    for (i in selectedCircles.indices) {
        if (selectedCircles[i] != correctPattern[i]) {
            return false
        }
    }
    return true
}
