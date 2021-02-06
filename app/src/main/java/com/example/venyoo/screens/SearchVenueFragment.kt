package com.example.venyoo.screens

import android.Manifest
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.venyoo.LocationService
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentSearchVenueBinding
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class SearchVenueFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var locationService: LocationService
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private val venueViewModel: VenueViewModel by viewModels{ viewModelFactory}

    private lateinit var binding: FragmentSearchVenueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher()

        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchVenueBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        venueViewModel.venues.observe(viewLifecycleOwner, Observer {
            venue -> Toast.makeText(requireContext(), "${venue.size}", Toast.LENGTH_SHORT).show()
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    venueViewModel.fetchVenue(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.discoverButton.setOnClickListener {
            getLocation()
        }
    }

    fun getLocation(){
        val result = locationService.getLocation()
        if(result is LocationService.Result.Success){
            //venueviewmodel.fetchVenue(latitude, longitude)
            Toast.makeText(requireContext(), "${result.latitude} & ${result.longitude}", Toast.LENGTH_SHORT).show()
        }else if (result is LocationService.Result.Failure){
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    private fun permissionLauncher(){
        requestPermissionLauncher = this.registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
            if(granted){
                getLocation()
            }
        }
    }

}