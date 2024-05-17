package com.lxy.responsivelayout.network

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * @Author：liuxy
 * @Date：2024/5/16 16:56
 * @Desc：
 *
 */

sealed interface NetDiagnosticUIState {
    val isLoading: Boolean
    val isSuccess: Boolean
    val error: String

    data class Success(

        val downloadSpeed: Int,
        val uploadSpeed: Int,
        val deviceName: String,
        val nowTime: String,
        val ipAddress: String,
        val ping: Long,
        val packetLoss: Double,
        val jitter: Long, // 抖动
        val signalStrength: Int,

        override val isLoading: Boolean,
        override val isSuccess: Boolean,
        override val error: String
    ) : NetDiagnosticUIState

    data class NoPost(

        override val isLoading: Boolean,
        override val isSuccess: Boolean,
        override val error: String
    ) : NetDiagnosticUIState
}

data class NetworkViewModelState(

    val downloadSpeed: Int = 0,
    val uploadSpeed: Int = 0,
    val deviceName: String = "",
    val nowTime: String = "",
    val ipAddress: String = "",
    val ping: Long = 0,
    val packetLoss: Double = 0.0,
    val jitter: Long = 0, // 抖动
    val signalStrength: Int = 0,

    val isLoading: Boolean,
    val isSuccess: Boolean = false,
    val error: String = ""
)

class NetworkDiagnosticViewModel() : ViewModel() {

    private val _ndUiState = MutableStateFlow(NetworkViewModelState(isLoading = true))

    val uiState : StateFlow<NetworkViewModelState> = _ndUiState


    fun refreshResp(context: Context){
        getPing(context)
        getDownloadSpeed()
    }

    private fun getPing(context : Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val tester = NetworkPerformanceTester(context, "192.168.11.163")
            val pingResult = tester.testPing()
            val signalStrength = tester.getWifiSignalStrength()
            _ndUiState.update {
                it.copy(
                    ping = pingResult.delay,
                    jitter = pingResult.jitter,
                    packetLoss = pingResult.packetLoss,
                    signalStrength = signalStrength,

                )
            }
        }
    }

    private fun getDownloadSpeed(){
        val speedTester = NetworkSpeedTester()
        viewModelScope.launch(Dispatchers.IO) {
            val downloadSpeed =
                speedTester.testDownloadSpeed("http://ash.icmp.hetzner.com/100MB.bin")
            _ndUiState.update {
               it.copy( downloadSpeed = downloadSpeed.toInt())
            }
        }
    }
}