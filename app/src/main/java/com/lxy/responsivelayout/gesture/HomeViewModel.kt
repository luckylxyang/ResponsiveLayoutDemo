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
    MiniAppEntity("1a","App1"),
    MiniAppEntity("2a","App2"),
    MiniAppEntity("3a","App3")
)

val otherMenu = listOf(
    MiniAppEntity("4a","App133234多发点撒"),
    MiniAppEntity("5a","App2"),
    MiniAppEntity("6a","App3"),
    MiniAppEntity("7a","App4"),
    MiniAppEntity("8a","App5"),
    MiniAppEntity("9a","App6"),
    MiniAppEntity("10a","App7")
)

private val hideMenu = listOf(
    MiniAppEntity("11a","App1"),
    MiniAppEntity("12a","App2"),
)

var allMenu = listOf(
    oftenMenu,
    otherMenu
)

class HomeViewModel : ViewModel() {

    private val _oftenMenus = MutableStateFlow(oftenMenu)
    val oftenMenus : StateFlow<List<MiniAppEntity>> = _oftenMenus

    private val _otherMenus = MutableStateFlow(allMenu)
    val otherMenus : StateFlow<List<List<MiniAppEntity>>> = _otherMenus

    private val _hideMenus = MutableStateFlow(hideMenu)
    val hideMenus : StateFlow<List<MiniAppEntity>> = _hideMenus

    private val _searchResult = MutableStateFlow(emptyList<MiniAppEntity>())
    val searchResult : StateFlow<List<MiniAppEntity>> = _searchResult

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

    fun removeOftenMenus(item : MiniAppEntity){
        _oftenMenus.update {
            val toMutableList = it.toMutableList()
            toMutableList.remove(item)
            toMutableList
        }
    }

    fun addToHideMenus(item : MiniAppEntity){
        _hideMenus.update {
            val toMutableList = it.toMutableList()
            toMutableList.add(item)
            toMutableList
        }
    }

    fun removeHideMenus(index : Int){
        _hideMenus.update {
            val toMutableList = it.toMutableList()
            toMutableList.removeAt(index)
            toMutableList
        }
    }

    fun removeHideMenus(item : MiniAppEntity){
        _hideMenus.update {
            val toMutableList = it.toMutableList()
            toMutableList.remove(item)
            toMutableList
        }
    }

    fun searchMiniApp(search : String){
        val result = mutableListOf<MiniAppEntity>()
        allMenu.forEach { entityList ->
            entityList.forEach{
                if (it.name.contains(search)){
                    result.add(it)
                }
            }
        }
        _searchResult.update {
            result
        }
    }

    fun clearSearchResult() {
        _searchResult.update {
            emptyList()
        }
    }

    fun onMove(fromIndex : Int, toIndex : Int, all : Int){
        allMenu = allMenu.toMutableList().apply {
            val list = allMenu[all].toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
            add(all, list)
        }
    }
}