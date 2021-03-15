package com.example.venyoo.screens

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import coil.load
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentVenueDetailBinding
import com.example.venyoo.screens.VenueViewModel
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class VenueDetailFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val venueViewModel: VenueViewModel by navGraphViewModels(R.id.venue_navigation){viewModelFactory}

    private lateinit var binding: FragmentVenueDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVenueDetailBinding.inflate(inflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        venueViewModel.currentVenue.observe(viewLifecycleOwner, Observer { venue ->
            if(venue.images.isNotEmpty()){
                binding.venueImageView.load(venue.images[0].url)
            }else{
                binding.venueImageView.load(R.drawable.ic_launcher_foreground)
            }
            binding.nameTextView.text = venue.name

            Log.d("TEST123", "name: ${venue.name}")

            val twitter: String = venue.social.twitter.handle
            if(twitter.isNullOrBlank()){
                binding.twitterTextView.visibility = View.GONE
            }else{
                binding.twitterTextView.text = twitter
                val url = "https://twitter.com/${twitter}"
                binding.twitterTextView.setOnClickListener {
                    try{
                        val intent = Intent("android.intent.action.Main")
                        intent.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"))
                        intent.addCategory("android.intent.category.LAUNCHER")
                        intent.setData(Uri.parse(url))
                        startActivity(intent)
                    }catch (e: ActivityNotFoundException){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }
                }
            }

            var address = venue.address.line1
            if(!venue.address.line2.isNullOrBlank()) address += ", ${venue.address.line2}"
            if(!venue.address.line3.isNullOrBlank()) address += ", ${venue.address.line3}"
            binding.addressTextView.text = address

            binding.cityStatePostalTextView.text = "${venue.city.name}, ${venue.state.stateCode} ${venue.postalCode}"

            if(venue.distance == null || venue.distance <= 0.0) binding.distanceTextView.text = "" else binding.distanceTextView.text = "${venue.distance} mi"
        })
    }
}