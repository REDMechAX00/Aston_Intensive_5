package com.redmechax00.astonintensive5.screens.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactsViewModel(initData: ArrayList<ContactModel>) : ViewModel() {

    private val contactsLiveMutable: MutableLiveData<ArrayList<ContactModel>> = MutableLiveData()
    val contactsLive: LiveData<ArrayList<ContactModel>> = contactsLiveMutable

    init {
        contactsLiveMutable.value = initData
    }

    fun updateContact(contact: ContactModel) {
        val data = contactsLive.value

        if (data != null) {
            val itemIndex = data.indexOf(data.find { it.itemId == contact.itemId })
            data[itemIndex] = contact

            updateLiveData(data)
        }
    }

    private fun updateLiveData(newData: ArrayList<ContactModel>) {
        contactsLiveMutable.value = newData
    }

}