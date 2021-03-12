package com.example.venyoo.screens

import android.Manifest
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.venyoo.LocationService
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentSearchVenueBinding
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import com.google.android.gms.common.api.ResolvableApiException
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchVenueFragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var locationService: LocationService

    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    private val venueViewModel: VenueViewModel by navGraphViewModels(R.id.venue_navigation){viewModelFactory}

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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    venueViewModel.fetchVenues(query)
                    navigateToVenueListFragment()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.discoverButton.setOnClickListener {
            if (locationService.checkLocationPermission()) {
                locationService.checkLocationSettings()
                    .addOnSuccessListener {
                        coroutineScope.launch {
                            temporarilyEnableLoading()
                            val result = locationService.getLocation()
                            if (result is LocationService.Result.Success) {
                                val latLong = "${result.latitude},${result.longitude}"
                                venueViewModel.fetchVenuesByCoordinates(latLong)
                                navigateToVenueListFragment()
                            } else if(result is LocationService.Result.Failure){
                                disableLoading()
                                Toast.makeText(requireContext(), "Failed to fetch location", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        if (exception is ResolvableApiException) {
                            // Location settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                exception.startResolutionForResult(
                                    activity,
                                    LocationService.REQUEST_CHECK_SETTINGS
                                )
                            } catch (sendEx: IntentSender.SendIntentException) {
                                // Ignore the error.
                            }
                        }
                    }
            } else {
                // Launches dialog that requests permission if not already granted.
                requestMultiplePermissionsLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }
    }

    private fun temporarilyEnableLoading() {
        binding.discoverButton.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
        Handler().postDelayed(Runnable {
            binding.discoverButton.isEnabled = true
            binding.progressBar.visibility = View.GONE
        }, 8000)
    }

    private fun disableLoading(){
        binding.discoverButton.isEnabled = true
        binding.progressBar.visibility = View.GONE
    }

    private fun navigateToVenueListFragment() {
        if (findNavController().currentDestination?.id != R.id.venue_list_fragment) {
            findNavController().navigate(R.id.action_fragment_search_venue_to_venueListFragment)
        }
    }

    /**
     * https://developer.android.com/training/permissions/requesting
     */
    private fun initPermissionLauncher() {
        requestMultiplePermissionsLauncher =
            this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                    //Do something here if permission granted for COARSE and FINE location
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.location_permission_denied,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }


}