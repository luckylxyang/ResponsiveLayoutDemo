package com.lxy.responsivelayout.main

import androidx.compose.ui.graphics.vector.ImageVector

/**
 *
 * @Author：liuxy
 * @Date：2024/4/11 15:28
 * @Desc：
 *
 */
data class BottomItem(
    val iconId: ImageVector,
    val name: String,
    var selected: Boolean = false,
)
