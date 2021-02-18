package com.example.venyoo.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.venyoo.databinding.FragmentVenueResultListBinding
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class VenueListFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentVenueResultListBinding

    private val venueListViewModel: VenueListViewModel by viewModels { viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentVenueResultListBinding.inflate(inflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}