package com.dist.baselibrary

import androidx.fragment.app.Fragment

/**
 *
 * @Author：liuxy
 * @Date：2024/6/19 15:23
 * @Desc：
 *
 */
interface SearchPageProvider {

    fun getTitle() : String

    fun getFragment(): Fragment
}