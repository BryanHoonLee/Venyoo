package com.example.venyoo.screens.common.fragments

import com.example.venyoo.dependencyinjection.presentation.PresentationModule
import com.example.venyoo.screens.common.activities.BaseActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule(this)
        )
    }

    protected val injector get() = presentationComponent
}