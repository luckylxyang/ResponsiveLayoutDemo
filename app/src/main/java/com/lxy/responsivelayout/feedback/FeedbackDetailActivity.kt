package com.lxy.responsivelayout.feedback

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lxy.responsivelayout.R
import com.lxy.responsivelayout.base.BaseComposeActivity
import com.lxy.responsivelayout.gesture.LazyGridDragAndDropDemo

/**
 *
 * @Author：liuxy
 * @Date：2024/5/29 13:42
 * @Desc：
 *
 */

class FeedbackDetailActivity : BaseComposeActivity() {
    @Composable
    override fun setContent() = LazyGridDragAndDropDemo() //FeedbackDetailScreenPage()

}

@Composable
private fun FeedbackDetailScreenPage(

) {
    Scaffold(
        topBar = {
            CenterTopBar()
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = colorResource(id = R.color.page_bg))
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            FeedbackContentPage(Modifier.weight(1f))

            Spacer(modifier = Modifier.padding(top = 10.dp))
            Button(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "提交")
            }
        }
    }
}

val paddingModifier = Modifier.padding(top = 10.dp)

@Composable
fun FeedbackContentPage(
    modifier: Modifier = Modifier
) {
    val list = remember {
        mutableListOf<Uri>()
    }
//    ImageSelectPage(photos = list,  modifier = Modifier.fillMaxWidth())
    ImageSelectPage(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(5.dp))
            .padding(10.dp),
        photos = list,
        headerContent = {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

                    Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("*")
                        }
                        append("产品建议")
                    })

                    Text(text = "0/200")
                }

                Spacer(modifier = paddingModifier)
                EditTextArea(value = "", onValueChange = {},  placeholder = "请填写不少于 10 字的描述", modifier = Modifier.fillMaxWidth())
                Spacer(modifier = paddingModifier)

                Text(text = "图片（提供问题截图）")
                Spacer(modifier = paddingModifier)
            }


        },

        footerContent = {
            Column {
                Spacer(modifier = paddingModifier)

                Text(text = "联系方式")
                Spacer(modifier = paddingModifier)
                EditTextArea(value = "", onValueChange = {

                },placeholder = "请填写联系方式方便与您联系", modifier = Modifier.fillMaxWidth())
                Spacer(modifier = paddingModifier)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = false, onCheckedChange = {})
                    Text(text = "允许开发者联系我")
                }
            }
        },
        onSelectMedia = {
            list.add(it)
        }
    )
}

@Composable
fun EditTextArea(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    minLine: Int = 1,
    maxLine: Int = 8,
    placeholder: String = "",
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(text = placeholder, color = Color.LightGray)
        },
        maxLines = maxLine,
        minLines = minLine,
        modifier = modifier
    )
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
            Text("用户反馈", fontSize = 16.sp)
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