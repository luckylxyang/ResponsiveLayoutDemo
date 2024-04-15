package com.lxy.responsivelayout

import android.app.Application
import androidx.startup.AppInitializer
import me.jessyan.autosize.InitProvider


/**
 *
 * @Author：liuxy
 * @Date：2024/4/15 9:23
 * @Desc：
 *
 */
class RLApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        AppInitializer.getInstance(baseContext)
//            .initializeComponent(InitProvider::class.java)
    }
}