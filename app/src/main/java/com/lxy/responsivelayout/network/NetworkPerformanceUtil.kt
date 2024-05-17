package com.lxy.responsivelayout.network

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 *
 * @Author：liuxy
 * @Date：2024/5/16 16:29
 * @Desc：
 *
 */

/**
 * 获取 ping 测试主机之间网络的连通性
 */

object NetworkPerformanceUtil {

    fun getPing(host: String, count: Int = 4): Long {
        try {
            val process = Runtime.getRuntime().exec("/system/bin/ping -c $count $host")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line!!.contains("avg")) {
                    // Example output: rtt min/avg/max/mdev = 12.345/56.789/123.456/7.890 ms
                    val parts = line!!.split("=".toRegex()).toTypedArray()[1].split("/".toRegex())
                        .toTypedArray()
                    Log.d("TAG", "getPing: $line")
                    return parts[1].toDouble().toLong() // Return average ping time
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }

    fun getPacketLoss(host: String, count: Int = 4): Double {
        try {
            val process = Runtime.getRuntime().exec("/system/bin/ping -c $count $host")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line!!.contains("packet loss")) {
                    // Example output: 4 packets transmitted, 4 received, 0% packet loss, time 3999ms
                    val parts = line!!.split(", ".toRegex()).toTypedArray()
                    Log.d("TAG", "getPacketLoss: $line")
                    return parts[2].replace("% packet loss".toRegex(), "").toDouble()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1.0
    }


    private fun calculateJitter(pingTimes: List<Long>): Long {
        if (pingTimes.size < 2) return 0
        var jitterSum = 0L
        for (i in 1 until pingTimes.size) {
            jitterSum += kotlin.math.abs(pingTimes[i] - pingTimes[i - 1])
        }
        return jitterSum / (pingTimes.size - 1)
    }

    fun getPingAndJitter(host: String, count: Int = 4): Pair<Long, Long> {
        val pingTimes = mutableListOf<Long>()
        for (i in 1..count) {
            val pingTime = getPing(host, 1)
            if (pingTime != -1L) {
                pingTimes.add(pingTime)
            }
        }
        val avgPing = pingTimes.average().toLong()
        val jitter = calculateJitter(pingTimes)
        return Pair(avgPing, jitter)
    }
}
