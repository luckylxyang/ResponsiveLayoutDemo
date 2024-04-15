package com.lxy.responsivelayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.tvExtend?.text = "我是横屏才展示"
    }


    private fun computeWindowSizeClasses() : WindowWidthSizeClass {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        val windowSizeClass = WindowSizeClass(widthDp = (width/density).toInt(), heightDp = (height/density).toInt())
        // COMPACT, MEDIUM, or EXPANDED
        val widthWindowSizeClass = windowSizeClass.windowWidthSizeClass
        // COMPACT, MEDIUM, or EXPANDED
        val heightWindowSizeClass = windowSizeClass.windowHeightSizeClass

        return widthWindowSizeClass
    }
}