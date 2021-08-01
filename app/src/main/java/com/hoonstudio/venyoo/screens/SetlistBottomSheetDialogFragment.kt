package com.hoonstudio.venyoo.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.databinding.BottomSheetDialogFragmentSetlistBinding
import com.hoonstudio.venyoo.screens.common.fragments.BaseBottomSheetDialogFragment
import com.hoonstudio.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class SetlistBottomSheetDialogFragment: BaseBottomSheetDialogFragment() {

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private val eventViewModel: EventViewModel by navGraphViewModels(R.id.venue_navigation){
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
            if(setResponse.isNotEmpty() && setResponse[0].sets.set.isNotEmpty()) {
                val set = setResponse[0].sets.set[0].song

                if (!set.isNullOrEmpty()) {
                    binding.notFoundImageView.visibility = View.GONE
                    binding.notFoundTextView.visibility = View.GONE
                    adapter.bindData(set)
                }else{
                    Toast.makeText(requireContext(), "TEST2", Toast.LENGTH_LONG).show()
                    binding.notFoundImageView.visibility = View.VISIBLE
                    binding.notFoundTextView.visibility = View.VISIBLE
                }
            }else{
                Toast.makeText(requireContext(), "TEST1", Toast.LENGTH_LONG).show()
                binding.notFoundImageView.visibility = View.VISIBLE
                binding.notFoundTextView.visibility = View.VISIBLE
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }

}