package com.lxy.responsivelayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.lxy.responsivelayout.addressbook.ContactsScreen
import com.lxy.responsivelayout.gesture.GestureDragPage
import com.lxy.responsivelayout.gesture.HomeEditScreenPage
import com.lxy.responsivelayout.gesture.LazyGridDragAndDropDemo
import com.lxy.responsivelayout.list.ListActivity
import com.lxy.responsivelayout.main.MainActivity
import com.lxy.responsivelayout.network.NetworkDiagnosticScreen
import com.lxy.responsivelayout.network.NetworkDiagnosticViewModel
import com.lxy.responsivelayout.ui.theme.ResponsiveLayoutTheme

class LoginActivity : ComponentActivity() {

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
//                    val windowSizeClass = calculateWindowSizeClass(this)
//                    Greeting(windowSizeClass)
//                    MainPage()
//                    GestureDragPage()
//                    NetworkDiagnosticScreen()
//                    ContactsScreen()
//                    HomeEditScreenPage()
                    LazyGridDragAndDropDemo()
                }
            }
        }

    }
}




@Composable
fun Greeting(windowSize: WindowSizeClass, modifier: Modifier = Modifier) {
    Log.d("TAG", "Greeting: ${windowSize.toString()}")
    if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact){
        landView()
    }else{
        hei()
    }
}

@Composable
fun landView(){
    val startActivityLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // 在这里处理启动活动的结果
        if (result.resultCode == Activity.RESULT_OK) {
            // 处理成功的情况
        }
    }
    val context = LocalContext.current
    Column {
        Text(text = "我是标题")
        Button(onClick = {
            startActivityLauncher.launch(Intent(context, MainActivity::class.java))
        }) {
            Text(text = "登录")
        }
        Button(onClick = {
            startActivityLauncher.launch(Intent(context, ListActivity::class.java))
        }) {
            Text(text = "列表")
        }
    }
}

@Composable
fun hei(){
    val startActivityLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // 在这里处理启动活动的结果
        if (result.resultCode == Activity.RESULT_OK) {
            // 处理成功的情况
        }
    }
    val context = LocalContext.current
    Row {
        Text(text = "我是标题")
        Button(onClick = {
            startActivityLauncher.launch(Intent(context, MainActivity::class.java))
        }) {
            Text(text = "登录")
        }
        Button(onClick = {
            startActivityLauncher.launch(Intent(context, ListActivity::class.java))
        }) {
            Text(text = "列表")
        }
    }
}
