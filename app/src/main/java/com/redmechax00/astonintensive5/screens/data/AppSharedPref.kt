package com.redmechax00.astonintensive5.screens.data

import android.content.Context
import android.content.SharedPreferences

class AppSharedPref(context: Context) {

    companion object {
        const val APP_SHARED_PREF = "app_shared_preference"
    }

    val sharedPref: SharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREF, Context.MODE_PRIVATE)
}