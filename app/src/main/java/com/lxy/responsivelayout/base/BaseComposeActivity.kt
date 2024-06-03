package com.lxy.responsivelayout.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.lxy.responsivelayout.ui.theme.ResponsiveLayoutTheme

/**
 *
 * @Author：liuxy
 * @Date：2024/5/30 14:41
 * @Desc：
 *
 */
abstract class BaseComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResponsiveLayoutTheme {
                setContent()
            }
        }
    }

    @Composable
    abstract fun setContent()

}