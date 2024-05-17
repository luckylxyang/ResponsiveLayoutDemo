package com.lxy.responsivelayout.network



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.NetworkInterface

/**
 *
 * @Author：liuxy
 * @Date：2024/5/16 16:11
 * @Desc：
 *
 */


@Composable
fun NetworkDiagnosticScreen(
    mViewModel: NetworkDiagnosticViewModel = viewModel()
) {
    var downloadUsage by remember { mutableStateOf("0 MB") }
    var uploadUsage by remember { mutableStateOf("0 MB") }
    var internalIP by remember { mutableStateOf("0.0.0.0") }
    var externalIP by remember { mutableStateOf("0.0.0.0") }

    val uiState = mViewModel.uiState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            internalIP = getLocalIpAddress()
            externalIP = getExternalIpAddress()
            mViewModel.refreshResp(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color(0xeeeff2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(6.dp))
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "本次测试结果相当于 45M宽带",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){

                Text(text = "下载使用数据: ${uiState.downloadSpeed} Mb", fontSize = 14.sp)
                Text(text = "上传使用数据: $uploadUsage", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .background(Color.Gray, shape = CircleShape)
                    .weight(1f)
                    .padding(32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "51", fontSize = 40.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Ping: ${uiState.ping}ms", fontSize = 12.sp)
                Text(text = "抖动: ${uiState.jitter}ms", fontSize = 12.sp)
                Text(text = "信号: ${uiState.signalStrength}dBM", fontSize = 12.sp)
                Text(text = "丢包: ${uiState.packetLoss}%", fontSize = 12.sp)
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        FilledTonalButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                mViewModel.refreshResp(context)
        }) {
            Text(text = "再测一次")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(6.dp))
                .padding(16.dp)
        ) {
            Text(text = "内部 IP: $internalIP", fontSize = 14.sp)
            Text(text = "外部 IP: $externalIP", fontSize = 14.sp)
        }

    }
}

// 获取本地 IP 地址
fun getLocalIpAddress(): String {
    val interfaces = NetworkInterface.getNetworkInterfaces()
    while (interfaces.hasMoreElements()) {
        val intf = interfaces.nextElement()
        val addrs = intf.inetAddresses
        while (addrs.hasMoreElements()) {
            val addr = addrs.nextElement()
            if (!addr.isLoopbackAddress && addr is InetAddress) {
                return addr.hostAddress
            }
        }
    }
    return "0.0.0.0"
}

// 获取外部 IP 地址（需要使用网络服务）
suspend fun getExternalIpAddress(): String {
    return withContext(Dispatchers.IO) {
        try {
            val url = java.net.URL("https://api.ipify.org?format=text")
            url.readText()
        } catch (e: Exception) {
            "0.0.0.0"
        }
    }
}
