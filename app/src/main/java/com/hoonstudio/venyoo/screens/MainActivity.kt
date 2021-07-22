package com.hoonstudio.venyoo.screens

import android.os.Bundle
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.screens.common.activities.BaseActivity

class MainActivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}