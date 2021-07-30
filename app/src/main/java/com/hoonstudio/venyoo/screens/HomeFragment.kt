package com.hoonstudio.venyoo.screens

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.hoonstudio.venyoo.LocationService
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.screens.common.fragments.BaseFragment
import com.hoonstudio.venyoo.screens.common.viewmodel.ViewModelFactory
import com.google.android.gms.common.api.ResolvableApiException
import com.hoonstudio.venyoo.databinding.FragmentHomeBinding
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var locationService: LocationService

    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    private val venueViewModel: VenueViewModel by navGraphViewModels(R.id.venue_navigation) { viewModelFactory }

    private lateinit var binding: FragmentHomeBinding

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
        binding = FragmentHomeBinding.inflate(inflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            v.clearFocus()
            navigateToSearchFragment()
        }

        binding.discoverButton.setOnClickListener {
            if (locationService.checkLocationPermission()) {
                locationService.checkLocationSettings()
                    .addOnSuccessListener {
                        coroutineScope.launch {
                            temporarilyEnableLoading(8000)
                            val result = locationService.getLocation()
                            if (result is LocationService.Result.Success) {
                                val latLong = "${result.latitude},${result.longitude}"
                                venueViewModel.fetchVenuesByCoordinates(latLong)
                                lifecycleScope.launch {
                                    delay(600)
                                    navigateToVenueListFragment()
                                }
                            } else if (result is LocationService.Result.Failure) {
                                disableLoading()
                                Toast.makeText(
                                    requireContext(),
                                    "Failed to fetch location",
                                    Toast.LENGTH_LONG
                                ).show()
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

    private fun temporarilyEnableLoading(delayTime: Long) {
        binding.discoverButton.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            delay(delayTime)
            disableLoading()
        }
    }

    private fun disableLoading() {
        binding.discoverButton.isEnabled = true
        binding.progressBar.visibility = View.GONE
    }

    private fun navigateToVenueListFragment() {
        if (findNavController().currentDestination?.id != R.id.venue_list_fragment){
            findNavController().navigate(HomeFragmentDirections.actionFragmentHomeToFragmentVenueList())
        }
    }

    private fun navigateToSearchFragment() {
        if (findNavController().currentDestination?.id != R.id.fragment_search){
            findNavController().navigate(HomeFragmentDirections.actionFragmentHomeToFragmentSearch())
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