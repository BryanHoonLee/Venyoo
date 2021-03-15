package com.example.venyoo.screens

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentVenueResultListBinding
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class VenueListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentVenueResultListBinding

    private lateinit var adapter: VenueResponseAdapter

    private var isDataLoaded = false


    private val venueViewModel: VenueViewModel by navGraphViewModels(R.id.venue_navigation){viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVenueResultListBinding.inflate(inflater)
        val view = binding.root

        adapter = VenueResponseAdapter { venueResponse ->
            venueViewModel.updateCurrentVenue(venueResponse)
            navigateToVenueDetailFragment()
        }


        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        venueViewModel.venueList.observe(viewLifecycleOwner, Observer { venues ->
            adapter.bindData(venues)
        })

        if(!isDataLoaded){
            isDataLoaded = true
            Handler().postDelayed(Runnable {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                if(adapter.itemCount == 0){
                    binding.pageNotFoundImageView.visibility = View.VISIBLE
                }
            }, 600)
        }else{
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun navigateToVenueDetailFragment(){
            if (findNavController().currentDestination?.id != R.id.venueDetailFragment) {
                findNavController().navigate(R.id.action_venue_list_fragment_to_venueDetailFragment)
        }
    }
}