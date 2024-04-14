package com.tshahakurov.myphonebook.data

data class ContactData(
    val id: Int? = -1,
    val name: String = "Default Contact",
    val phoneNumber: String = "+123123123123",
    val image: String? = null
)