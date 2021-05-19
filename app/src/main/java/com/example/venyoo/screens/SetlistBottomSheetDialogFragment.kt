package com.example.venyoo.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venyoo.R
import com.example.venyoo.databinding.BottomSheetDialogFragmentSetlistBinding
import com.example.venyoo.screens.common.fragments.BaseBottomSheetDialogFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class SetlistBottomSheetDialogFragment: BaseBottomSheetDialogFragment() {

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private val eventViewModel: EventViewModel by navGraphViewModels(R.id.event_navigation){
        viewmodelFactory
    }

    private lateinit var adapter: SetlistAdapter

    private lateinit var binding: BottomSheetDialogFragmentSetlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetDialogFragmentSetlistBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = SetlistAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventViewModel.setlist.observe(viewLifecycleOwner, Observer { setResponse ->
            Log.d("TEST", " or ${setResponse}")
            val set = setResponse[0].sets.set[0].song
            if(!set.isNullOrEmpty()){
                adapter.bindData(set)
            }
        })
    }

}