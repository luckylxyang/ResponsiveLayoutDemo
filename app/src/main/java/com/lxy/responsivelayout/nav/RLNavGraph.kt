
package com.lxy.responsivelayout.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.lxy.responsivelayout.entity.Article
import com.lxy.responsivelayout.main.ArticleDetail
import com.lxy.responsivelayout.main.ArticleList


const val POST_ID = "postId"

@Composable
fun RLNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = RLDestinations.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = RLDestinations.HOME_ROUTE,
//            deepLinks = listOf(
//                navDeepLink {
//                    uriPattern =
//                        "$JETNEWS_APP_URI/${RLDestinations.HOME_ROUTE}?$POST_ID={$POST_ID}"
//                }
//            )
        ) { navBackStackEntry ->
            val list = arrayListOf<Article>(
                Article("我是一个标题", "https://developer.android.google.cn/guide/topics/large-screens/support-different-screen-sizes?hl=zh-cn", "test"),
                Article("我是二个标题", "https://developer.android.google.cn/develop/ui/compose/layouts/adaptive?hl=zh-cn", "test2"),
            )
//            ArticleList(messages = list, navController = navController)
        }
        composable(RLDestinations.INTERESTS_ROUTE) {

//            it.arguments?.getString("url")?.let { it1 -> ArticleDetail(url = it1) }
        }
    }
}
