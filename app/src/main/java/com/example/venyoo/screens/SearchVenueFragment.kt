package com.example.venyoo.screens

import android.Manifest
import android.content.IntentSender
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.venyoo.LocationService
import com.example.venyoo.databinding.FragmentSearchVenueBinding
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchVenueFragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var locationService: LocationService
    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    private val venueViewModel: VenueViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentSearchVenueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionLauncher()

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

        venueViewModel.venues.observe(viewLifecycleOwner, Observer { venue ->
            Toast.makeText(requireContext(), "${venue.size}", Toast.LENGTH_SHORT).show()
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    venueViewModel.fetchVenue(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.discoverButton.setOnClickListener {
            if (locationService.checkLocationPermission()) {
                coroutineScope.launch{
                    if(locationService.checkLocationSettings()){
                        Toast.makeText(requireContext(), "WORKED", Toast.LENGTH_SHORT).show()
                    }
                }

//                val result = locationService.getLatitudeLongitude()
//                if (result is LocationService.Result.Success) {
//
//                }
            } else {
                requestMultiplePermissionsLauncher.launch(
                        arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        )
                )
            }
        }
    }

    /**
     * https://developer.android.com/training/permissions/requesting
     */
    private fun initPermissionLauncher() {
        requestMultiplePermissionsLauncher = this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                //Do something here if permission granted for COARSE and FINE location
            }
        }
    }


}