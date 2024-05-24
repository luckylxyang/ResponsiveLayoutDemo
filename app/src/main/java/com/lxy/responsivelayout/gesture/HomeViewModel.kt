package com.lxy.responsivelayout.gesture

import android.view.MenuItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 *
 * @Author：liuxy
 * @Date：2024/5/24 16:06
 * @Desc：
 *
 */
private val oftenMenu = listOf(
    MiniAppEntity("App1"),
    MiniAppEntity("App2"),
    MiniAppEntity("App3")
)

private val otherMenu = listOf(
    MiniAppEntity("App1"),
    MiniAppEntity("App2"),
    MiniAppEntity("App3"),
    MiniAppEntity("App4"),
    MiniAppEntity("App5"),
    MiniAppEntity("App6"),
    MiniAppEntity("App7")
)
class HomeViewModel : ViewModel() {

    private val _oftenMenus = MutableStateFlow(oftenMenu)
    val oftenMenus : StateFlow<List<MiniAppEntity>> = _oftenMenus

    private val _otherMenus = MutableStateFlow(otherMenu)
    val otherMenus : StateFlow<List<MiniAppEntity>> = _otherMenus

    fun addOftenMenus(item: MiniAppEntity){
        _oftenMenus.update {
            val toMutableList = it.toMutableList()
            toMutableList.add(item)
            toMutableList
        }
    }

    fun removeOftenMenus(index : Int){
        _oftenMenus.update {
            val toMutableList = it.toMutableList()
            toMutableList.removeAt(index)
            toMutableList
        }
    }
}