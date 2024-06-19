package com.lxy.responsivelayout

import android.app.Application
import android.util.Log
import androidx.startup.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.InitProvider
import me.jessyan.autosize.utils.AutoSizeUtils


/**
 *
 * @Author：liuxy
 * @Date：2024/4/15 9:23
 * @Desc：
 *
 */
@HiltAndroidApp
class RLApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        AppInitializer.getInstance(baseContext)
//            .initializeComponent(InitProvider::class.java)


        val init = AutoSize.checkInit()
        Log.d("TAG", "onCreate autosize init: $init")
    }
}