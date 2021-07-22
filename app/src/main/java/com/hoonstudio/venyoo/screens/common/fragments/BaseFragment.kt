package com.hoonstudio.venyoo.screens.common.fragments

import androidx.fragment.app.Fragment
import com.hoonstudio.venyoo.dependencyinjection.presentation.PresentationModule
import com.hoonstudio.venyoo.screens.common.activities.BaseActivity

open class BaseFragment: Fragment(){
    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(PresentationModule(this)
        )
    }

    protected val injector get() = presentationComponent
}