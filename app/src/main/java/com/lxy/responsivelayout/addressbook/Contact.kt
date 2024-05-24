package com.lxy.responsivelayout.addressbook

import net.sourceforge.pinyin4j.PinyinHelper

/**
 *
 * @Author：liuxy
 * @Date：2024/5/17 17:10
 * @Desc：
 *
 */
data class Contact(
    val name: String,
    val pinyin: String = ""
)

fun String.toPinyin(): String {
    val pinyinArray = PinyinHelper.toHanyuPinyinStringArray(this[0])
    return pinyinArray?.firstOrNull()?.substring(0, 1)?.toUpperCase() ?: this
}
