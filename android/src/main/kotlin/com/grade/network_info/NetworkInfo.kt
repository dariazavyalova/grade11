
package com.grade.network_info

import android.net.ConnectivityManager
import android.os.Build
import java.net.*
import android.content.Context

internal class NetworkInfo() { 
    var connectivityManager : ConnectivityManager? = null
    
    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { 
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }

    public fun networkType() : String =
       connectivityManager?.getActiveNetworkInfo()?.getTypeName() ?: "Cannot get network info"

    public fun listenNetworkTypeChagnes(networkCallback: ConnectivityManager.NetworkCallback) {
        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
    }
}