package com.lxy.responsivelayout.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.WindowMetricsCalculator
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.databinding.ActivityListBinding
import com.lxy.responsivelayout.entity.Article
import com.lxy.responsivelayout.main.MainViewModel

class ListActivity : AppCompatActivity() {
    private val binding: ActivityListBinding by lazy {
        ActivityListBinding.inflate(layoutInflater)
    }

    private val mViewModel: ListViewModel by lazy {
        ViewModelProvider(this)[ListViewModel::class.java]
    }

    private val adapter: ListAdapter by lazy {
        val adapter = ListAdapter()
        adapter.setOnItemClickListener { view, t, position ->
            mViewModel.setSelectItem(t)
            binding.slide.open()
        }

        adapter
    }

    val list = arrayListOf<Article>(
        Article(
            "我是一个标题",
            "https://developer.android.google.cn/guide/topics/large-screens/support-different-screen-sizes?hl=zh-cn",
            "test"
        ),
        Article(
            "我是二个标题",
            "https://developer.android.google.cn/develop/ui/compose/layouts/adaptive?hl=zh-cn",
            "test2"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        computeWindowSizeClasses()
        binding.rv.let {
            it.layoutManager = LinearLayoutManager(this@ListActivity)
            it.adapter = adapter
        }
        adapter.setData(list)


    }

    private fun computeWindowSizeClasses() {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        val windowSizeClass = WindowSizeClass(widthDp = (width/density).toInt(), heightDp = (height/density).toInt())
        // COMPACT, MEDIUM, or EXPANDED
        val widthWindowSizeClass = windowSizeClass.windowWidthSizeClass
        // COMPACT, MEDIUM, or EXPANDED
        val heightWindowSizeClass = windowSizeClass.windowHeightSizeClass

        mViewModel.setExtends(widthWindowSizeClass == WindowWidthSizeClass.EXPANDED)
    }
}