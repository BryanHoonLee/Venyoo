package com.hoonstudio.venyoo.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.databinding.FragmentVenueResultListBinding
import com.hoonstudio.venyoo.screens.common.fragments.BaseFragment
import com.hoonstudio.venyoo.screens.common.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            if(venues.isEmpty()){
                binding.pageNotFoundImageView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }else{
                binding.pageNotFoundImageView.visibility = View.GONE
            }
        })

        if(!isDataLoaded){
            isDataLoaded = true
            lifecycleScope.launch {
                delay(600)
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }else{
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun navigateToVenueDetailFragment(){
            if (findNavController().currentDestination?.id != R.id.venueDetailFragment) {
                findNavController().navigate(VenueListFragmentDirections.actionVenueListFragmentToVenueDetailFragment())
        }
    }
}