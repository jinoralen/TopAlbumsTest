package com.jinoralen.topalbums.core

import android.content.Context
import android.net.ConnectivityManager


fun Context.isConnected(): Boolean {
    val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connMgr.activeNetworkInfo?.isConnected == true
}
