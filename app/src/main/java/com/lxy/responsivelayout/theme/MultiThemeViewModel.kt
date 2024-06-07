package com.lxy.responsivelayout.theme

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 *
 * @Author：liuxy
 * @Date：2024/6/3 15:13
 * @Desc：
 *
 */
class MultiThemeViewModel : ViewModel() {

    private val _themeListState = MutableStateFlow(emptyList<ThemeEntity>())

    val themeList : StateFlow<List<ThemeEntity>> = _themeListState

    init {
        _themeListState.update {
            mutableListOf<ThemeEntity>().also {
                it.add(ThemeEntity("蓝色", Color.Blue, "blue"))
                it.add(ThemeEntity("红色", Color.Red, "red"))
                it.add(ThemeEntity("red", Color.Blue, "blue"))
                it.add(ThemeEntity("red", Color.Blue, "blue"))
            }
        }
    }
}