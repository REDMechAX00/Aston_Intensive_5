package com.redmechax00.astonintensive5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.redmechax00.astonintensive5.extension.startContactsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startContactsFragment()
        }
    }
}