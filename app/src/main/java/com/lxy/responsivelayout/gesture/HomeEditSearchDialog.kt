package com.lxy.responsivelayout.gesture

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lxy.responsivelayout.R

/**
 *
 * @Author：liuxy
 * @Date：2024/5/27 10:42
 * @Desc：
 *
 */

@Composable
fun HomeEditSearchDialog(
    onDismissRequest: () -> Unit,
    onSearch: (String) ->Unit,
    menuList: List<MiniAppEntity>,
    resultCompose : @Composable (MiniAppEntity)->Unit
){
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit){
        keyboardController?.show()
    }
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        var searchText by remember { mutableStateOf("") }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(Color.White)
            ) {

                BasicTextField(
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .height(65.dp),
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                        ) {
                            Icon(Icons.Filled.Search, null)
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                innerTextField()
                            }
                            Text(text = "取消", modifier = Modifier.clickable {
                                onDismissRequest()
                            })
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        keyboardController?.hide()
                        onSearch(searchText)
                    })
                )

                LazyColumn(content = {
                    items(menuList.size) {
                        val item = menuList[it]
                        resultCompose(item)

                    }
                })

            }
        }
    }
}