package com.hoonstudio.venyoo.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.databinding.FragmentVenueResultListBinding
import com.hoonstudio.venyoo.screens.common.fragments.BaseFragment
import com.hoonstudio.venyoo.screens.common.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttractionListFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentVenueResultListBinding

    private lateinit var adapter: AttractionAdapter

    private var isDataLoaded: Boolean = false

    private val attractionViewModel: AttractionViewModel by navGraphViewModels(R.id.venue_navigation){viewModelFactory}

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

        adapter = AttractionAdapter{ attractionResponse ->
            attractionViewModel.updateCurrentAttraction(attractionResponse)

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attractionViewModel.attractions.observe(viewLifecycleOwner, Observer { attractions ->
            adapter.bindData(attractions)
            if(attractions.isEmpty()){
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
}