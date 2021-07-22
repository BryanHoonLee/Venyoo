package com.hoonstudio.venyoo.screens.common.fragments

import com.hoonstudio.venyoo.dependencyinjection.presentation.PresentationModule
import com.hoonstudio.venyoo.screens.common.activities.BaseActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule(this)
        )
    }

    protected val injector get() = presentationComponent
}