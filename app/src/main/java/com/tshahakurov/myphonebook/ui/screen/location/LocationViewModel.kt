package com.tshahakurov.myphonebook.ui.screen.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.tshahakurov.myphonebook.receiver.NetworkReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class LocationViewModel : ViewModel() {

    val isNetworkConnected = MutableStateFlow(false)
    val currentLocation = MutableStateFlow("")

    fun startObservingNetworkState() {
        viewModelScope.launch {
            NetworkReceiver.isNetworkConnected.collect { isConnected ->
                isNetworkConnected.value = isConnected
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Permission wasn't granted")
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            val geocoder = Geocoder(context, Locale.getDefault())
            val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                ?: listOf()
            currentLocation.value = address[0].getAddressLine(0)
        }
    }


}