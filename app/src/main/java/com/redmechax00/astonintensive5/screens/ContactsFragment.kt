package com.redmechax00.astonintensive5.screens

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.redmechax00.astonintensive5.R
import com.redmechax00.astonintensive5.extension.startInfoFragment
import com.redmechax00.astonintensive5.screens.data.*

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    companion object {
        const val KEY_CONTACT_ID = "contact_id"
        const val SHARED_PREFS_ITEM = "shared_preferences_item_"
    }

    private lateinit var contacts: List<View>

    private lateinit var viewModel: ContactsViewModel
    private var data: ArrayList<ContactModel> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields(view)
        setItemsOnClickListener()
        observeViewModel()
    }

    private fun initFields(view: View) {
        //Toolbar
        val requireActivity = requireActivity()
        val toolbarContacts = requireActivity.findViewById(R.id.toolbar_contacts) as Toolbar

        if (requireActivity is AppCompatActivity) {
            requireActivity.setSupportActionBar(toolbarContacts)
            requireActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        //Items
        contacts = listOf(
            view.findViewById(R.id.contact_item1),
            view.findViewById(R.id.contact_item2),
            view.findViewById(R.id.contact_item3),
            view.findViewById(R.id.contact_item4)
        )

        //ViewModel
        viewModel = ViewModelProvider(
            requireActivity(),
            ContactsViewModelFactory(initData())
        )[ContactsViewModel::class.java]
    }

    private fun initData(): ArrayList<ContactModel> {
        val prefs = AppSharedPref(requireContext()).sharedPref

        val savedData = arrayListOf<ContactModel>()
        val gson = Gson()

        var i = 0
        var stringData = prefs.getString(SHARED_PREFS_ITEM + 0, null)

        while (stringData != null) {
            val contact: ContactModel =
                gson.fromJson(stringData, ContactModel::class.java)
            savedData.add(contact)

            i++
            stringData = prefs.getString(SHARED_PREFS_ITEM + i, null)
        }

        return savedData.ifEmpty { ContactsObject.getItems(requireContext()) }
    }

    private fun setItemsOnClickListener() {
        contacts.forEach {
            it.setOnClickListener(itemsOnClickListener)
        }
    }

    private fun observeViewModel() {
        viewModel.contactsLive.observe(viewLifecycleOwner) { data ->
            updateItemsData(data)
            saveDataToCache(data)
        }
    }

    private fun updateItemsData(newData: ArrayList<ContactModel>) {
        data = newData

        contacts.forEachIndexed { i, item ->
            val fullName: TextView = item.findViewById(R.id.item_contact_full_name)
            val phone: TextView = item.findViewById(R.id.item_contact_phone)
            fullName.text = data[i].toString()
            phone.text = data[i].itemPhone
        }
    }

    private fun saveDataToCache(newData: ArrayList<ContactModel>) {
        val editor: SharedPreferences.Editor = AppSharedPref(requireContext()).sharedPref.edit()
        val gson = Gson()
        newData.forEachIndexed { i, item ->
            val json = gson.toJson(item)
            editor.putString(SHARED_PREFS_ITEM + i, json)
        }
        editor.apply()
    }

    private val itemsOnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.contact_item1 -> {
                startInfoFragment(data[0].itemId)
            }
            R.id.contact_item2 -> {
                startInfoFragment(data[1].itemId)
            }
            R.id.contact_item3 -> {
                startInfoFragment(data[2].itemId)
            }
            R.id.contact_item4 -> {
                startInfoFragment(data[3].itemId)
            }
        }
    }

    private fun startInfoFragment(itemId: Int) {
        val bundle = Bundle()
        bundle.putInt(KEY_CONTACT_ID, itemId)
        requireActivity().startInfoFragment(bundle)
    }

}