package com.lxy.responsivelayout.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dist.baselibrary.SearchPageProvider
import com.lxy.responsivelayout.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *
 * @Author：liuxy
 * @Date：2024/6/19 15:25
 * @Desc：
 *
 */

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val binding : ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var searchPageProviders: Set<@JvmSuppressWildcards SearchPageProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.viewPager.adapter = SearchPagerAdapter(supportFragmentManager, searchPageProviders.toList())
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    class SearchPagerAdapter(
        fm: FragmentManager,
        private val providers: List<SearchPageProvider>
    ) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return providers.size
        }

        override fun getItem(position: Int): Fragment {
            return providers[position].getFragment()
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return providers[position].getTitle()
        }
    }
}