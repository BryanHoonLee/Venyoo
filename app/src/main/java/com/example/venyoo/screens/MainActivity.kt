package com.example.venyoo.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.venyoo.R
import com.example.venyoo.screens.common.activities.BaseActivity

class MainActivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
//        injector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}