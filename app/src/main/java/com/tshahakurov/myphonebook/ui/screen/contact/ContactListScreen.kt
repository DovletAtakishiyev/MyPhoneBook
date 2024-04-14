package com.tshahakurov.myphonebook.ui.screen.contact

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.tshahakurov.myphonebook.R
import com.tshahakurov.myphonebook.data.ContactData
import com.tshahakurov.myphonebook.ui.screen.location.ErrorScreen
import com.tshahakurov.myphonebook.ui.screen.location.TAG
import com.tshahakurov.myphonebook.ui.theme.ContactElementHeight
import com.tshahakurov.myphonebook.ui.theme.ContactElementRadius
import com.tshahakurov.myphonebook.ui.theme.ContactImageRadius
import com.tshahakurov.myphonebook.ui.theme.ContactImageSize
import com.tshahakurov.myphonebook.ui.theme.DetailAccent
import com.tshahakurov.myphonebook.ui.theme.HeaderFirst
import com.tshahakurov.myphonebook.ui.theme.HeaderSecond
import com.tshahakurov.myphonebook.ui.theme.MainBackground
import com.tshahakurov.myphonebook.ui.theme.MainForeground
import com.tshahakurov.myphonebook.ui.theme.SmallPadding
import com.tshahakurov.myphonebook.ui.theme.ThinLine
import com.tshahakurov.myphonebook.ui.theme.VerySmallPadding

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactListScreen(
    viewModel: ContactListViewModel = viewModel()
) {
    val contactList by viewModel.contactList.collectAsState(arrayListOf())

    //----------------------------------------------------------------------------------------\\
    //                                 Permission                                             ||
    //----------------------------------------------------------------------------------------\\
    val contactPermissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)
    val isPermitted by viewModel.contactPermission.collectAsState()
    val requestContactsPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.contactPermission.value = true
            Log.d(TAG, "CONTACTS Granted")
        } else {
            viewModel.contactPermission.value = false
            Log.d(TAG, "CONTACTS NOT Granted")
        }
    }
    //----------------------------------------------------------------------------------------//
    //                                 Permission                                             ||
    //----------------------------------------------------------------------------------------//

    if (!isPermitted) {
        ErrorScreen(msg = "Contact permission not granted")
        LaunchedEffect(contactPermissionState) {
            requestContactsPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    } else {
        viewModel.loadContacts(LocalContext.current)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBackground),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                contactList.forEach {
                    ContactListElement(it)
                }
            }
        }
    }
}

@Composable
fun ContactListElement(contact: ContactData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(ContactElementHeight)
            .padding(horizontal = SmallPadding, vertical = VerySmallPadding)
            .border(
                border = BorderStroke(ThinLine, DetailAccent),
                shape = RoundedCornerShape(ContactElementRadius)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = VerySmallPadding)
        ) {
            Image(
                painter = if (contact.image == null) painterResource(id = R.drawable.img)
                else rememberAsyncImagePainter(contact.image),
                contentDescription = "contact image",
                modifier = Modifier
                    .size(ContactImageSize)
                    .clip(
                        RoundedCornerShape(ContactImageRadius)
                    )
            )
            Column(
                modifier = Modifier
                    .weight(0.5f, true)
                    .padding(horizontal = SmallPadding)
            ) {
                Text(
                    text = contact.name,
                    fontSize = HeaderFirst,
                    style = TextStyle(color = MainForeground)
                )
                Text(
                    text = contact.phoneNumber,
                    fontSize = HeaderSecond,
                    style = TextStyle(color = MainForeground)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactListScreenPreview() {
    ContactListScreen()
}