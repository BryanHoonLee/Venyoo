package com.hoonstudio.venyoo.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.databinding.FragmentSearchBinding
import com.hoonstudio.venyoo.screens.common.fragments.BaseFragment
import com.hoonstudio.venyoo.screens.common.viewmodel.ViewModelFactory
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchFragment: BaseFragment() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val venueViewModel: VenueViewModel by navGraphViewModels(R.id.venue_navigation){viewModelFactory}

    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.clearFocus()
        binding.searchView.isFocusable = true
        binding.searchView.requestFocus()
        binding.searchView.onActionViewExpanded()
        binding.searchView.isIconified = true
        binding.searchView.isIconified = false
        binding.searchView.setQuery("", false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    coroutineScope.launch {
                        temporarilyEnableLoading(8000)
                        if (binding.venueRadioButton.isChecked) {
                            venueViewModel.fetchVenues(query)
                            lifecycleScope.launch {
                                delay(600)
                                navigateToVenueListFragment()
                            }
                        }
                        if (binding.artistRadioButton.isChecked) {

                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun temporarilyEnableLoading(delayTime: Long) {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            delay(delayTime)
            disableLoading()
        }
    }

    private fun disableLoading(){
        binding.progressBar.visibility = View.GONE
    }

    private fun navigateToVenueListFragment() {
        if(findNavController().currentDestination?.id != R.id.venue_list_fragment) {
            findNavController().navigate(SearchFragmentDirections.actionFragmentSearchToFragmentVenueList())
        }
    }
}