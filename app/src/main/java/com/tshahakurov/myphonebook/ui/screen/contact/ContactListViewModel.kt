package com.tshahakurov.myphonebook.ui.screen.contact

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tshahakurov.myphonebook.data.ContactData
import com.tshahakurov.myphonebook.provider.ContactContentResolver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContactListViewModel: ViewModel() {

    val contactList = MutableStateFlow(arrayListOf<ContactData>())
    // *  Вот это вот 'contactPermission' нужно было для проверки.
    // *  Как я понял Система, либо сама реализация лаунчера
    // *  не даёт запрашивать разрешение больше 2х раз (если предыдущие
    // *  запросы на разрешение были NotGranted)
    val contactPermission = MutableStateFlow(false)

    fun loadContacts(context: Context){
        viewModelScope.launch {
            contactList.value = ContactContentResolver.getContactList(context)
        }
    }
}