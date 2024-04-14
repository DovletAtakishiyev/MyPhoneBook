package com.tshahakurov.myphonebook.provider

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import com.tshahakurov.myphonebook.data.ContactData

@SuppressLint("Recycle", "Range")
object ContactContentResolver {
    fun getContactList(context: Context): ArrayList<ContactData> {
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null,
        )

        val contactList = arrayListOf<ContactData>()
        var i = 0
        cursor?.let{
            if ((it.count > 0)) {
                while (it.moveToNext()) {
                    contactList.add(
                        ContactData(
                            i++,
                            it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                            it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                            it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)),
                        )
                    )
                }
            }
        }
        return contactList
    }
}