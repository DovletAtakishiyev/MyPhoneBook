package com.tshahakurov.myphonebook.ui.screen.location

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.tshahakurov.myphonebook.ui.theme.ContactElementRadius
import com.tshahakurov.myphonebook.ui.theme.DetailAccent
import com.tshahakurov.myphonebook.ui.theme.HeaderFirst
import com.tshahakurov.myphonebook.ui.theme.HeaderSecond
import com.tshahakurov.myphonebook.ui.theme.MainBackground
import com.tshahakurov.myphonebook.ui.theme.MainForeground
import com.tshahakurov.myphonebook.ui.theme.MapElementSize
import com.tshahakurov.myphonebook.ui.theme.MediumPadding
import com.tshahakurov.myphonebook.ui.theme.ThinLine

const val TAG = "suita"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    viewModel: LocationViewModel = viewModel()
) {

    viewModel.startObservingNetworkState()
    val isConnected by viewModel.isNetworkConnected.collectAsState(initial = false)
    val location by viewModel.currentLocation.collectAsState("")

    //----------------------------------------------------------------------------------------\\
    //                                 Permission                                             ||
    //----------------------------------------------------------------------------------------\\
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "LOCATION Granted")
        } else {
            Log.d(TAG, "LOCATION NOT Granted")
        }
    }
    //----------------------------------------------------------------------------------------//
    //                                 Permission                                             ||
    //----------------------------------------------------------------------------------------//

    if (!isConnected) {
        ErrorScreen("Check your connection")
    } else if (locationPermissionState.status != PermissionStatus.Granted) {
        ErrorScreen("Location permission not granted")
        LaunchedEffect(locationPermissionState) {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBackground),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val context = LocalContext.current
                Text(
                    text = "Check your current location!",
                    fontSize = HeaderFirst,
                    style = TextStyle(color = MainForeground),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(MediumPadding))
                Box(
                    modifier = Modifier
                        .size(MapElementSize)
                        .border(
                            border = BorderStroke(ThinLine, DetailAccent),
                            shape = RoundedCornerShape(ContactElementRadius)
                        )
                )
                Spacer(modifier = Modifier.height(MediumPadding))
                Text(
                    text = location,
                    fontSize = HeaderSecond,
                    style = TextStyle(color = MainForeground),
                )
                Spacer(modifier = Modifier.height(MediumPadding))
                Button(onClick = {
                    viewModel.getLocation(context)
                }) {
                    Text(text = "Check Location")
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(msg: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = msg,
            fontSize = HeaderFirst,
            style = TextStyle(color = MainForeground),
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    LocationScreen()
}