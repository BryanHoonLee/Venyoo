package com.example.venyoo.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.example.venyoo.MyApplication
import com.example.venyoo.dependencyinjection.activity.ActivityModule
import com.example.venyoo.dependencyinjection.presentation.PresentationModule

open class BaseActivity: AppCompatActivity(){

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent by lazy{
        appComponent.newActivityComponent(ActivityModule())
    }

    private val presentationComponent by lazy{
        activityComponent.newPresentationComponent(PresentationModule(this))
    }

    protected val injector get() = presentationComponent
}