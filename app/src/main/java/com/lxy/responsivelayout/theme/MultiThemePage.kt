package com.lxy.responsivelayout.theme

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 *
 * @Author：liuxy
 * @Date：2024/6/3 15:08
 * @Desc：
 *
 */

@Composable
fun MultiThemeScreenPage(
    mViewModel : MultiThemeViewModel = viewModel()
){
    Scaffold(
        topBar = {
            CenterTopBar()
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CenterTopBar(
) {
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("个性换肤", fontSize = 16.sp)
        },
        navigationIcon = {
            IconButton(onClick = {
                (context as ComponentActivity).finish()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}