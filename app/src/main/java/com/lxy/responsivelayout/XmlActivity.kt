package com.lxy.responsivelayout

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.WindowMetricsCalculator
import com.lxy.responsivelayout.databinding.ActivityXmlBinding

class XmlActivity : AppCompatActivity() {

    private val binding: ActivityXmlBinding by lazy {
        ActivityXmlBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (computeWindowSizeClasses() == WindowWidthSizeClass.EXPANDED){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            binding.tvExtend?.text = "我是横屏才展示"
        }else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }


    private fun computeWindowSizeClasses() : WindowWidthSizeClass {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        //Xml: width = 1080, height = 2340, density = 2.88
        // no size: width = 1080, height = 2340, density = 2.625
        Log.d("TAG", "Xml: width = $width, height = $height, density = $density")
        val windowSizeClass = WindowSizeClass(widthDp = (width/density).toInt(), heightDp = (height/density).toInt())
        // COMPACT, MEDIUM, or EXPANDED
        val widthWindowSizeClass = windowSizeClass.windowWidthSizeClass
        // COMPACT, MEDIUM, or EXPANDED
        val heightWindowSizeClass = windowSizeClass.windowHeightSizeClass

        Log.d("TAG", "Xml: ${windowSizeClass.toString()}")
        return widthWindowSizeClass
    }
}