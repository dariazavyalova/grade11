package com.grade.network_info

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import android.net.ConnectivityManager

internal class NetworkInfoMethodChannelHandler(private val networkInfo: NetworkInfo, private val networkCallback: ConnectivityManager.NetworkCallback ) : MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method) {
            "getNetworkType" -> {
                networkInfo.listenNetworkTypeChagnes(networkCallback)
                result.success(networkInfo.networkType()) 
            } 
            else -> result.notImplemented()
        }
    }
}