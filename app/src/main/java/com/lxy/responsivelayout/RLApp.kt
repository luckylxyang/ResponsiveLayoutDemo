package com.lxy.responsivelayout

import android.app.Application
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

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

        AutoSizeConfig.getInstance().unitsManager
            .setSupportDP(false)
            .setSupportSP(false).supportSubunits = Subunits.PT
    }
}