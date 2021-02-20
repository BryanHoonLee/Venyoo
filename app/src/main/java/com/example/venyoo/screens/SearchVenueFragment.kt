package com.example.venyoo.screens

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.venyoo.LocationService
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentSearchVenueBinding
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchVenueFragment : BaseFragment() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var locationService: LocationService
    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    private val venueViewModel: VenueViewModel by activityViewModels { viewModelFactory }

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
            Toast.makeText(requireContext(), "${venue[0].name}, ${venue[0].city}, ${venue[0].address}, ${venue[0].city}, ${venue[0].description}", Toast.LENGTH_LONG).show()
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    venueViewModel.fetchMultipleVenues(query)
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
                Log.d("GUH1", "hahahaha")
                coroutineScope.launch{
                    Log.d("GUH2", "hahaha")
                    if(locationService.checkLocationSettings()){
                        Log.d("GUH3", "hahaha")
                        val result = locationService.getLatitudeLongitude()
                        if(result is LocationService.Result.Success){
                            val latLong = "${result.latitude},${result.longitude}"
                            venueViewModel.fetchMultipleVenuesByCoordinates(latLong)
                            navigateToVenueListFragment()
                            venueViewModel.test("TEST1")
                        }
                    }
                }
                venueViewModel.test("TEST2")
//                navigateToVenueListFragment()
            }
            else {
                requestMultiplePermissionsLauncher.launch(
                        arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        )
                )
            }
        }
    }

    private fun navigateToVenueListFragment(){
        if(findNavController().currentDestination?.id != R.id.venue_list_fragment){
            findNavController().navigate(R.id.action_fragment_search_venue_to_venueListFragment)
        }
    }

    /**
     * https://developer.android.com/training/permissions/requesting
     */
    private fun initPermissionLauncher() {
        requestMultiplePermissionsLauncher = this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                //Do something here if permission granted for COARSE and FINE location
            }else{
                Toast.makeText(requireContext(), R.string.location_permission_denied, Toast.LENGTH_LONG).show()
            }
        }
    }


}