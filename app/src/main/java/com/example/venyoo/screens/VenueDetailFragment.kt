package com.example.venyoo.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentVenueDetailBinding
import com.example.venyoo.screens.VenueViewModel
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class VenueDetailFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val venueViewModel: VenueViewModel by navGraphViewModels(R.id.venue_navigation){viewModelFactory}

    private lateinit var binding: FragmentVenueDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVenueDetailBinding.inflate(inflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        venueViewModel.currentVenue.observe(viewLifecycleOwner, Observer { venue ->
            binding.nameTextView.text = venue.name
        })
    }
}