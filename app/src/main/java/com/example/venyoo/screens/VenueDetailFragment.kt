package com.example.venyoo.screens

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import coil.load
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentVenueDetailBinding
import com.example.venyoo.screens.VenueViewModel
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class VenueDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val venueViewModel: VenueViewModel by navGraphViewModels(R.id.venue_navigation) { viewModelFactory }

    private lateinit var binding: FragmentVenueDetailBinding
    private var trayIsOpen = true

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
//            if (venue.images.isNotEmpty()) {
//                binding.venueImageView.load(venue.images[0].url)
//            } else {
//                binding.venueImageView.load(R.drawable.ic_launcher_foreground)
//            }

            /** VENUE NAME **/
            binding.nameTextView.text = venue.name

            /** DISTANCE **/
            if (venue.distance == null || venue.distance <= 0.0) {
                binding.distanceTextView.text = ""
            } else binding.distanceTextView.text = "${venue.distance} mi"

            /** TWITTER **/
            val twitter: String = venue.social.twitter.handle
            if (twitter.isNullOrBlank()) {
                binding.twitterTextView.text = "-"
                binding.openHoursTextView.isClickable = false
            } else {
                binding.twitterTextView.text = twitter
                val url = "https://twitter.com/${twitter}"
                binding.twitterTextView.setOnClickListener {
                    try {
                        val intent = Intent("android.intent.action.Main")
                        intent.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"))
                        intent.addCategory("android.intent.category.LAUNCHER")
                        intent.setData(Uri.parse(url))
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }
                }
            }

            /** PHONE **/
            val phoneNumber: String = venue.boxOfficeInfo.phoneNumberDetail
            if (phoneNumber.isEmpty()) {
                binding.phoneNumberTextView.text = "-"
            } else {
                binding.phoneNumberTextView.text = phoneNumber
                Linkify.addLinks(binding.phoneNumberTextView, Linkify.PHONE_NUMBERS)
            }

            /** ADDRESS **/
            var address = venue.address.line1
            if (!venue.address.line2.isNullOrBlank()) address += ", ${venue.address.line2}"
            if (!venue.address.line3.isNullOrBlank()) address += ", ${venue.address.line3}"
            address += ", ${venue.city.name}, ${venue.state.stateCode} ${venue.postalCode}"
            binding.addressTextView.text = address
            binding.addressTextView.setOnClickListener {
                val uri = Uri.parse("geo:0,0?q=${address}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent);
                }
            }

            /** TWITTER **/
            var openHours: String = venue.boxOfficeInfo.openHoursDetail
            if (openHours.isEmpty()) {
                binding.openHoursTextView.text = "-"
                binding.openHoursTextView.isClickable = false
            } else {
                binding.openHoursTextView.text = openHours
                binding.openHoursTextView.setOnClickListener {
                    if (binding.openHoursTextView.maxLines == 1) {
                        TransitionManager.beginDelayedTransition(binding.trayRoot, AutoTransition())
                        binding.openHoursTextView.maxLines = 10
                    } else {
                        TransitionManager.beginDelayedTransition(binding.trayRoot, AutoTransition())
                        binding.openHoursTextView.maxLines = 1
                    }
                }
            }

            /** TRAY ARROW **/
            binding.trayArrowImageView.setOnClickListener{
                if(trayIsOpen){
                    TransitionManager.beginDelayedTransition(binding.trayRoot, AutoTransition())
                    binding.trayArrowImageView.load(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    binding.trayGroup.visibility = View.GONE
                }else{
                    TransitionManager.beginDelayedTransition(binding.trayRoot, AutoTransition())
                    binding.trayArrowImageView.load(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    binding.trayGroup.visibility = View.VISIBLE
                }
                trayIsOpen = !trayIsOpen
            }
        })
    }
}