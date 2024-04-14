package com.tshahakurov.myphonebook

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tshahakurov.myphonebook.receiver.NetworkReceiver
import com.tshahakurov.myphonebook.ui.screen.navigation.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(
            NetworkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        setContent {
            HomeScreen()
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(NetworkReceiver)
    }
}