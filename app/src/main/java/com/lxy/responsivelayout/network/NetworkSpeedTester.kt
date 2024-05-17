package com.lxy.responsivelayout.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis

/**
 *
 * @Author：liuxy
 * @Date：2024/5/17 11:32
 * @Desc：
 *
 */

class NetworkSpeedTester() {

    // 测试下载速度
    suspend fun testDownloadSpeed(testUrl: String): Double {
        return withContext(Dispatchers.IO) {
            val url = URL(testUrl)
            var downloadedBytes = 0L
            val timeTakenMillis = measureTimeMillis {
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.connect()

                val inputStream = BufferedInputStream(connection.inputStream)
                val buffer = ByteArray(1024)
                var bytesRead: Int

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    downloadedBytes += bytesRead
                }

                inputStream.close()
                connection.disconnect()
            }

            // 计算下载速度（Mbps）
            val speedMbps = (downloadedBytes * 8 / 1_000_000.0) / (timeTakenMillis / 1000.0)
            speedMbps.roundToInt().toDouble()
        }
    }

    // 测试上传速度
    suspend fun testUploadSpeed(testUrl: String, uploadData: ByteArray): Double {
        return withContext(Dispatchers.IO) {
            val url = URL(testUrl)
            val timeTakenMillis = measureTimeMillis {
                val connection = url.openConnection() as HttpURLConnection
                connection.doOutput = true
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/octet-stream")

                val outputStream = BufferedOutputStream(connection.outputStream)
                outputStream.write(uploadData)
                outputStream.flush()
                outputStream.close()

                connection.responseCode // Ensure the request is sent
                connection.disconnect()
            }

            // 计算上传速度（Mbps）
            val speedMbps = (uploadData.size * 8 / 1_000_000.0) / (timeTakenMillis / 1000.0)
            speedMbps.roundToInt().toDouble()
        }
    }
}