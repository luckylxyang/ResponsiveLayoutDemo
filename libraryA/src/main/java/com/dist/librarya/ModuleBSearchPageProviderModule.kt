package com.dist.librarya

import androidx.fragment.app.Fragment
import com.dist.baselibrary.SearchPageProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 *
 * @Author：liuxy
 * @Date：2024/6/19 15:56
 * @Desc：
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class ModuleBSearchPageProviderModule {
    @Singleton
    @Provides
    @IntoSet
    fun provideModuleBSearchPageProvider(): SearchPageProvider {
        return object : SearchPageProvider {
            override fun getFragment(): Fragment {
                return ModuleBFragment()
            }

            override fun getTitle(): String {
                return "Module B"
            }
        }
    }
}
