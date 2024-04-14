package com.tshahakurov.myphonebook.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tshahakurov.myphonebook.util.isNetworkConnected
import kotlinx.coroutines.flow.MutableStateFlow

object NetworkReceiver : BroadcastReceiver() {
    val isNetworkConnected = MutableStateFlow(false)
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        isNetworkConnected.value = context?.isNetworkConnected() == true
    }
}