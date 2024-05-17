package com.lxy.responsivelayout.network

import android.content.Context
import android.net.wifi.WifiManager
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.util.Log
import java.net.InetAddress

/**
 *
 * @Author：liuxy
 * @Date：2024/5/17 10:17
 * @Desc：
 *
 */
class NetworkPerformanceTester(private val context: Context, private val host: String) {

    data class PingResult(
        val delay: Long,
        val jitter: Long,
        val packetLoss: Double
    )

    fun testPing(count: Int = 4, timeout: Int = 1000): PingResult {
        var totalDelay = 0L
        var previousDelay = -1L
        var totalJitter = 0L
        var packetLossCount = 0

        repeat(count) {
            val startTime = System.currentTimeMillis()
            val reached = try {
                InetAddress.getByName(host).isReachable(timeout)
            } catch (e: Exception) {
                false
            }
            val endTime = System.currentTimeMillis()

            if (reached) {
                val delay = endTime - startTime
                totalDelay += delay

                if (previousDelay >= 0) {
                    val jitter = kotlin.math.abs(delay - previousDelay)
                    totalJitter += jitter
                }

                previousDelay = delay
            } else {
                packetLossCount++
            }
        }

        val packetLoss = (packetLossCount / count.toDouble()) * 100
        val averageDelay = if (count - packetLossCount > 0) totalDelay / (count - packetLossCount) else 0
        val averageJitter = if (count - packetLossCount > 1) totalJitter / (count - packetLossCount - 1) else 0

        return PingResult(averageDelay, averageJitter, packetLoss)
    }

    fun getWifiSignalStrength(): Int {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        wifiInfo.ssid?.let {
            Log.d("TAG", "getWifiSignalStrength: $it")
        }
        return WifiManager.calculateSignalLevel(wifiInfo.rssi, 100)
    }

    fun getMobileSignalStrength(): Int {
        var signalStrengthValue = -1

        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val phoneStateListener = object : PhoneStateListener() {
            override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
                super.onSignalStrengthsChanged(signalStrength)
                signalStrengthValue = if (signalStrength.isGsm) {
                    signalStrength.gsmSignalStrength
                } else {
                    signalStrength.cdmaDbm
                }
            }
        }
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)
        // Unregister the listener after fetching the value
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)

        return signalStrengthValue
    }
}
