package com.lxy.responsivelayout.feedback

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.InputStream

/**
 *
 * @Author：liuxy
 * @Date：2024/5/30 10:32
 * @Desc：
 *
 */

@Composable
fun ImageSelectPage(
    modifier : Modifier = Modifier,
    photos : List<Uri> = emptyList(),
    maxSize : Int = 9,
    hasMaxLimit : Boolean = true,
    itemContent : @Composable (Uri)->Unit = { PhotoItem(path = it)},
    loadMoreContent : @Composable () -> Unit = { DefaultAddItem() },
    headerContent : @Composable (() -> Unit)?,
    footerContent : @Composable (() -> Unit)?,
    onSelectMedia : (Uri) -> Unit,
){
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        selectedImageUri = uri
    }

    selectedImageUri?.let {
        onSelectMedia(it)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 90.dp),
        modifier = modifier,
        contentPadding = PaddingValues(5.dp),
    ) {

        item(span = {
            GridItemSpan(maxLineSpan)
        }) {
            if (headerContent != null) {
                headerContent()
            }
        }

        items(photos.size) { index ->
            itemContent(photos[index])
        }

        if (!hasMaxLimit || photos.size < maxSize){
            item {
                Box(
                    modifier = Modifier.clickable {
                        imagePickerLauncher.launch(PickVisualMediaRequest())
                    },
                    contentAlignment = Alignment.Center
                ){
                    loadMoreContent()
                }
            }
        }

        item(span = {
            GridItemSpan(maxLineSpan)
        }){
            if (footerContent != null) {
                footerContent()
            }
        }

    }
}

@Composable
private fun PhotoItem(
    path : Uri
){
    Box(){
        val bitmap = loadBitmapFromUri(path)
        bitmap?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}


@Composable
private fun DefaultAddItem(
    modifier: Modifier = Modifier,
){
    Box(modifier = modifier.padding(5.dp)){
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Localized description"
        )
    }
}

@Composable
private fun loadBitmapFromUri(uri: Uri): ImageBitmap? {
    val contentResolver = LocalContext.current.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
    return bitmap?.asImageBitmap()
}

@Composable
private fun loadImageMax(){

}