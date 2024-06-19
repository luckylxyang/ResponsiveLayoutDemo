package com.lxy.responsivelayout

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import me.jessyan.autosize.AutoSize

/**
 *
 * @Author：liuxy
 * @Date：2024/4/16 11:27
 * @Desc：
 *
 */
class AutoSizeInitializer : Initializer<Unit> {
    override fun create(context: Context) {
//        AutoSize.checkAndInit(context.applicationContext as Application)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}