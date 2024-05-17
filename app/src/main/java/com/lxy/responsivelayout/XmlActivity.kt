package com.lxy.responsivelayout

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.WindowMetricsCalculator
import com.lxy.responsivelayout.databinding.ActivityXmlBinding
import java.net.InetAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder

class XmlActivity : AppCompatActivity() {

    private val binding: ActivityXmlBinding by lazy {
        ActivityXmlBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (computeWindowSizeClasses() == WindowWidthSizeClass.EXPANDED){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            binding.tvExtend?.text = "我是横屏才展示"
        }else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        getDevicesID()
    }


    private fun computeWindowSizeClasses() : WindowWidthSizeClass {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        // Xml: width = 1080, height = 2340, density = 2.88
        // no size: width = 1080, height = 2340, density = 2.625
        Log.d("TAG", "Xml: width = $width, height = $height, density = $density")
        val windowSizeClass = WindowSizeClass(widthDp = (width/density).toInt(), heightDp = (height/density).toInt())
        // COMPACT, MEDIUM, or EXPANDED
        val widthWindowSizeClass = windowSizeClass.windowWidthSizeClass
        // COMPACT, MEDIUM, or EXPANDED
        val heightWindowSizeClass = windowSizeClass.windowHeightSizeClass

        Log.d("TAG", "Xml: ${windowSizeClass}")
        return widthWindowSizeClass
    }

    fun getDevicesID(){
//        val intent = Intent("com.gsOneMap", Uri.parse("info://111"))
//
//        intent.putExtra("userName", "")
//        intent.putExtra("password", "")
//        startActivity(intent)

        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("TAG", "getDevicesID: androidId = $androidId")
        Log.d("TAG", "getDevicesID: androidId = ${getNetworkInfo(this)}")

    }

    fun getNetworkInfo(context: Context): String {
        // 获取网络运营商信息
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkOperatorName = telephonyManager.networkOperatorName

        // 获取设备的IP地址
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val ipAddress = networkInfo?.let {
            if (it.isConnected) {
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val ipAddressInt = wifiManager.connectionInfo.ipAddress

                val wifiInfo: WifiInfo? = wifiManager.connectionInfo
                val ssid: String = wifiInfo?.ssid ?: "Not connected"

                // 如果ssid以引号包围，则去除引号
                Log.d("TAG", "getDevicesID: androidId = ${ssid.replace("\"", "")}")

                return@let InetAddress.getByAddress(
                    ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipAddressInt).array()
                ).hostAddress

            }
            null
        } ?: "Not connected"

        return "Network Operator: $networkOperatorName\nIP Address: $ipAddress"
    }
}