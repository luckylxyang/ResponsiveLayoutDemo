package com.lxy.responsivelayout.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.nav.RLNavGraph
import com.lxy.responsivelayout.nav.RLNavigationActions
import com.lxy.responsivelayout.ui.theme.ResponsiveLayoutTheme
import kotlinx.coroutines.launch

/**
 *
 * @Author：liuxy
 * @Date：2024/4/11 15:23
 * @Desc：
 *
 */

val icons = arrayOf(
    BottomItem(Icons.Filled.Home, "工作台", true),
    BottomItem(Icons.Filled.Person, "主页"),
)

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResponsiveLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSizeClass = calculateWindowSizeClass(this)
                    Log.d("TAG", "Greeting: ${windowSizeClass.toString()}")
//                    MainApp(windowSizeClass.widthSizeClass)
                    MyApp(windowSizeClass.widthSizeClass)
                }
            }
        }
    }
}

@Composable
fun MyApp(widthSizeClass: WindowWidthSizeClass) {
    // Select a navigation element based on window size.
    val navController = rememberNavController()

    when (widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            ExpandedScreen(navController)
        }
        else ->{
            CompactScreen(navController)
        }
    }
}

@Composable
fun CompactScreen(navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                icons.forEach { item ->
                    NavigationBarItem(
                        selected = item.selected,
                        onClick = { },
                        icon = {
                            Icon(item.iconId, contentDescription = item.name)
                        })
                }
            }
        },
        content = {
            it.calculateBottomPadding()
            RLNavGraph(
                isExpandedScreen = false,
                navController = navController,
                openDrawer = {  },
            )
        })
}

@Composable
fun MediumScreen(navController: NavHostController) {
    PermanentNavigationDrawer(
        drawerContent = {
            icons.forEach { item ->
                NavigationDrawerItem(
                    icon = {
                        Icon(item.iconId, contentDescription = item.name)
                    },
                    label = { },
                    selected = item.selected,
                    onClick = { }
                )
            }
        },
        content = {
            // Other content
        }
    )
}

@Composable
fun ExpandedScreen(navController: NavHostController) {


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationRail {
                icons.forEach { item ->
                    NavigationRailItem(
                        selected = item.selected,
                        onClick = { },
                        icon = {
                            Icon(item.iconId, contentDescription = item.name)
                        })
                }
            }
        },
        content = {
            it.calculateBottomPadding()
            RLNavGraph(
                isExpandedScreen = false,
                navController = navController,
                openDrawer = {  },
            )
        })
}