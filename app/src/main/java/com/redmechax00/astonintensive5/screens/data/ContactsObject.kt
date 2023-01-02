package com.redmechax00.astonintensive5.screens.data

import android.content.Context
import com.redmechax00.astonintensive5.R
import com.redmechax00.astonintensive5.extension.formatToPhone

object ContactsObject {

    fun getItems(context: Context): ArrayList<ContactModel> =
        listOfContacts(nameList(context), surnameList(context))

    private fun listOfContacts(
        nameList: List<String>,
        surnameList: List<String>
    ): ArrayList<ContactModel> {
        val contactsList = arrayListOf<ContactModel>()
        for (i in nameList.indices) {
            contactsList.add(
                ContactModel(
                    getUid(),
                    nameList[i],
                    surnameList[i],
                    randomPhoneNumber()
                )
            )
        }
        return contactsList
    }

    private fun nameList(context: Context): List<String> = listOf(
        context.getString(R.string.text_name1),
        context.getString(R.string.text_name2),
        context.getString(R.string.text_name3),
        context.getString(R.string.text_name4),
    )

    private fun surnameList(context: Context): List<String> = listOf(
        context.getString(R.string.text_surname1),
        context.getString(R.string.text_surname2),
        context.getString(R.string.text_surname3),
        context.getString(R.string.text_surname4),
    )

    private var countId: Int = 0
    private fun getUid(): Int = countId++

    private fun randomPhoneNumber(): String {
        var phone = "+1"
        for (i in 0 until 10) {
            val rnd = (0..9).random()
            phone += rnd.toString()
        }
        return phone.formatToPhone()
    }

}
