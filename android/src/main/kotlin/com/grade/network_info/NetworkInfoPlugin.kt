package com.grade.network_info

import androidx.annotation.NonNull
import android.content.Context
import android.net.* 
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler 
import android.os.Looper 

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.EventChannel



/** NetworkInfoPlugin */
class NetworkInfoPlugin: FlutterPlugin, EventChannel.StreamHandler {
  private lateinit var channel : MethodChannel

  private lateinit var eventChannel: EventChannel
  private var eventSink: EventChannel.EventSink? = null 

  private var uiThreadHandler = Handler(Looper.getMainLooper())
  private lateinit var networkInfo : NetworkInfo

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    networkInfo = NetworkInfo()
    networkInfo.create(flutterPluginBinding.applicationContext)

    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "network_info")
    eventChannel = EventChannel(flutterPluginBinding.binaryMessenger, "network_info_listener")
    eventChannel.setStreamHandler(this)

   

    val methodCallHandler = NetworkInfoMethodChannelHandler(networkInfo, networkCallback) 
    channel.setMethodCallHandler(methodCallHandler)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    eventChannel.setStreamHandler(null)
  }

  private val networkCallback = object : ConnectivityManager.NetworkCallback() {
    // network is available for use
    override fun onAvailable(network: Network) {   
        uiThreadHandler.post {
          eventSink?.success(networkInfo.networkType())
        }
         super.onAvailable(network)
    }
    // Network capabilities have changed for the network
    override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        uiThreadHandler.post {
          eventSink?.success(networkInfo.networkType())
        }
    }
    // lost network connection
    override fun onLost(network: Network) {
       uiThreadHandler.post {
          eventSink?.success(networkInfo.networkType())
        }
        super.onLost(network)
    }
  }

  override fun onListen(arguments: Any?, events: EventChannel.EventSink) {
    this.eventSink = events
  }

   override fun onCancel(arguments: Any?) {
    this.eventSink = null
  }

}
