package com.tshahakurov.myphonebook.util

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkConnected() =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
        activeNetworkInfo?.isConnected == true
    }