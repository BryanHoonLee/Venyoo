package com.hoonstudio.venyoo.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.hoonstudio.venyoo.MyApplication
import com.hoonstudio.venyoo.dependencyinjection.activity.ActivityModule
import com.hoonstudio.venyoo.dependencyinjection.presentation.PresentationModule

open class BaseActivity: AppCompatActivity(){

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent by lazy{
        appComponent.newActivityComponent(ActivityModule(this))
    }

    private val presentationComponent by lazy{
        activityComponent.newPresentationComponent(PresentationModule(this))
    }

    protected val injector get() = presentationComponent
}