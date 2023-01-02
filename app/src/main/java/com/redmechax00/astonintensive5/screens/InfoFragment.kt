package com.redmechax00.astonintensive5.screens

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.redmechax00.astonintensive5.R
import com.redmechax00.astonintensive5.extension.find
import com.redmechax00.astonintensive5.extension.isTablet
import com.redmechax00.astonintensive5.screens.ContactsFragment.Companion.KEY_CONTACT_ID
import com.redmechax00.astonintensive5.screens.data.ContactModel
import com.redmechax00.astonintensive5.screens.data.ContactsViewModel

class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var toolbarInfo: Toolbar
    private lateinit var toolbarBtnBack: ImageButton
    private lateinit var toolbarBtnDone: ImageButton

    private lateinit var viewModel: ContactsViewModel
    private var item: ContactModel? = null
    private var itemId: Int? = null

    private val contactName by find<EditText>(R.id.info_contact_name)
    private val contactSurname by find<EditText>(R.id.info_contact_surname)
    private val contactPhone by find<EditText>(R.id.info_contact_phone)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        formatInput()
        setOnClickListeners()
    }

    private fun initFields() {
        val requireActivity = requireActivity()

        //Toolbar
        toolbarInfo = requireActivity.findViewById(R.id.toolbar_info)
        toolbarBtnBack = toolbarInfo.findViewById(R.id.info_toolbar_btn_back)
        toolbarBtnDone = toolbarInfo.findViewById(R.id.info_toolbar_btn_done)

        if (requireActivity is AppCompatActivity) {
            requireActivity.setSupportActionBar(toolbarInfo)
            requireActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        viewModel = ViewModelProvider(requireActivity())[ContactsViewModel::class.java]

        itemId = arguments?.getInt(KEY_CONTACT_ID)
        item = viewModel.contactsLive.value?.find { it.itemId == itemId }

        contactName.setText(item?.itemName)
        contactSurname.setText(item?.itemSurname)
        contactPhone.setText(item?.itemPhone)
    }

    private fun formatInput() {
        contactPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }

    private fun setOnClickListeners() {
        toolbarBtnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        toolbarBtnDone.setOnClickListener {
            val contact = ContactModel(
                itemId ?: -1,
                contactName.text.toString(),
                contactSurname.text.toString(),
                contactPhone.text.toString()
            )
            viewModel.updateContact(contact)

            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isTablet(requireActivity())) {
            showTabletContainer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isTablet(requireActivity())) {
            hideTabletContainer()
        }
    }

    private fun showTabletContainer() {
        val mainSecondContainer =
            requireActivity().findViewById<View>(R.id.main_second_fragment_container)
        mainSecondContainer.visibility = View.VISIBLE
    }

    private fun hideTabletContainer() {
        val mainSecondContainer =
            requireActivity().findViewById<View>(R.id.main_second_fragment_container)
        mainSecondContainer.visibility = View.GONE
    }
}