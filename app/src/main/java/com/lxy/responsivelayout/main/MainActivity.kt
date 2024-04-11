package com.lxy.responsivelayout.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.ui.theme.ResponsiveLayoutTheme

/**
 *
 * @Author：liuxy
 * @Date：2024/4/11 15:23
 * @Desc：
 *
 */

val icons = arrayOf(
    BottomItem(R.drawable.login_icon_person, "工作台", true),
    BottomItem(R.drawable.login_icon_person, "主页"),
    BottomItem(R.drawable.login_icon_person, "我的"),
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
    NavHost(navController = navController, startDestination = "welcome") {
//        composable("welcome") { WelcomePage(navController) }
//        composable("login") { loginPage(navController) }
//        composable("home") { HomePage(navController) }

    }
    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CompactScreen(navController)
        }

        WindowWidthSizeClass.Medium -> {
            MediumScreen()
        }

        WindowWidthSizeClass.Expanded -> {
            ExpandedScreen()
        }
    }
}

@Composable
fun CompactScreen(navController: NavHostController) {

    Scaffold(bottomBar = {
        NavigationBar {
            icons.forEach { item ->
                NavigationBarItem(
                    selected = item.selected,
                    onClick = { },
                    icon = {
                        Image(
                            painter = painterResource(id = item.iconId),
                            contentDescription = null
                        )
                    })
            }
        }
    }) {
        it.calculateTopPadding()
    }
}

@Composable
fun MediumScreen() {
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail {
            icons.forEach { item ->
                NavigationRailItem(
                    selected = item.selected,
                    onClick = { },
                    icon = {
                        Image(
                            painter = painterResource(id = item.iconId),
                            contentDescription = null
                        )
                    })
            }
        }
        // Other content
    }
}

@Composable
fun ExpandedScreen() {
    PermanentNavigationDrawer(
        drawerContent = {
            icons.forEach { item ->
                NavigationDrawerItem(
                    icon = {
                        Image(
                            painter = painterResource(id = item.iconId),
                            contentDescription = null
                        )
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