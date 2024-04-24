package com.lxy.responsivelayout.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lxy.responsivelayout.entity.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

class MainViewModel : ViewModel() {

    private val _select  = MutableLiveData<Article>()
    val select : LiveData<Article> = _select

    fun setSelectItem(article: Article){
        _select.postValue(article)
    }

    private val _back  = MutableLiveData<Boolean>()
    val back : LiveData<Boolean> = _back

    fun back(back : Boolean){
        _back.postValue(back)
    }


    private val _dragList  = MutableLiveData<List<String>>()
    val dragList : LiveData<List<String>> = _dragList

    fun init(){

    }
}