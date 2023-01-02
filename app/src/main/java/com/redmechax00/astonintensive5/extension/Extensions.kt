package com.redmechax00.astonintensive5.extension

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.redmechax00.astonintensive5.R
import com.redmechax00.astonintensive5.screens.ContactsFragment
import com.redmechax00.astonintensive5.screens.InfoFragment
import java.util.*

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T : View> Fragment.find(idRes: Int) =
    unsafeLazy<T> { this.requireActivity().findViewById(idRes) }

fun AppCompatActivity.startContactsFragment() {
    this.supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        .replace(R.id.main_first_fragment_container, ContactsFragment())
        .commit()
}

fun FragmentActivity.startInfoFragment(bundle: Bundle) {
    if (backStackIsNotEmpty()) {
        this.supportFragmentManager.popBackStack()
    }

    val infoFrag = InfoFragment()
    infoFrag.arguments = bundle

    val transaction = this.supportFragmentManager.beginTransaction()

    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)

    transaction.addToBackStack(null)

    if (isTablet(this)) {
        transaction.replace(R.id.main_second_fragment_container, infoFrag)
    } else {
        transaction.replace(R.id.main_first_fragment_container, infoFrag)
    }

    transaction.commit()
}

fun FragmentActivity.backStackIsNotEmpty(): Boolean =
    this.supportFragmentManager.backStackEntryCount != 0

fun isTablet(context: Context): Boolean =
    context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE

fun String.formatToPhone(): String =
    PhoneNumberUtils.formatNumber(this, Locale.getDefault().country)




