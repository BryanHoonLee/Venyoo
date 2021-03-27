package com.example.venyoo.screens

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentVenueDetailBinding
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class VenueDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val venueViewModel: VenueViewModel by navGraphViewModels(R.id.venue_navigation) { viewModelFactory }
    private val eventViewModel: EventViewModel by navGraphViewModels(R.id.venue_navigation) { viewModelFactory }

    private lateinit var venueImageAdapter: VenueImageAdapter
    private lateinit var venueEventAdapter: EventAdapter
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

        venueImageAdapter = VenueImageAdapter { image ->
            Toast.makeText(requireContext(), "${image.url} CLICKED", Toast.LENGTH_LONG).show()
        }
        binding.venueImageRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.venueImageRecyclerView.adapter = venueImageAdapter

        venueEventAdapter = EventAdapter { event ->
            Toast.makeText(requireContext(), "Date: ${event.dates.start.dateTBA} | ${event.dates.start.dateTime} | ${event.name}: ${event._embedded.attractions[0].name} CLICKED", Toast.LENGTH_LONG).show()
        }
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.eventRecyclerView.adapter = venueEventAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        venueViewModel.currentVenue.observe(viewLifecycleOwner, Observer { venue ->

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
            if (phoneNumber.isBlank()) {
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

            /** Open Hours **/
            var openHours: String = venue.boxOfficeInfo.openHoursDetail
            if (openHours.isBlank()) {
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

            /** URL **/
            var url: String = venue.url
            if(url.isBlank()){
                binding.urlTextView.text = "-"
                binding.urlTextView.isClickable = false
            }else {
                binding.urlTextView.text = url
                binding.urlTextView.setOnClickListener {
                    if(binding.urlTextView.maxLines == 1){
                        TransitionManager.beginDelayedTransition(binding.trayRoot, AutoTransition())
                        binding.urlTextView.maxLines = 10
                    }else{
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
            }

            /** TRAY ARROW **/
            binding.trayArrowImageView.setOnClickListener{
                val transition = AutoTransition()
                transition.duration = 500
                if(trayIsOpen){
                    binding.trayArrowImageView.load(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    binding.trayGroup.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(binding.root, transition)
                }else{
                    binding.trayArrowImageView.load(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    binding.trayGroup.visibility = View.VISIBLE
                    TransitionManager.beginDelayedTransition(binding.root, transition)
                }
                trayIsOpen = !trayIsOpen
            }

            eventViewModel.fetchVenueEvents(venue.id)
            venueViewModel.fetchFourSquareVenue("${venue.location.latitude},${venue.location.longitude}")
        })

        venueViewModel.additionalCurrentVenueInfo.observe(viewLifecycleOwner, Observer{ venue ->
            venueViewModel.fetchFourSquarePhotos(venue[0].id)
        })

        venueViewModel.additionalCurrentVenuePhotos.observe(viewLifecycleOwner, Observer {
            venueViewModel.fetchImages()
        })

        venueViewModel.imageList.observe(viewLifecycleOwner, Observer { images ->
            if(images != null){
                /** VENUE IMAGES **/
                venueImageAdapter.bindData(images)
            }
        })

        eventViewModel.venueEventList.observe(viewLifecycleOwner, Observer { events ->
            Log.d("EVENT", "eventList: ${events.size}")
            if(!events.isNullOrEmpty()){
                venueEventAdapter.bindData(events)
                binding.eventRecyclerView.visibility = View.VISIBLE
                binding.noUpcomingEventsGroup.visibility = View.GONE
            }else{
                binding.eventRecyclerView.visibility = View.GONE
                binding.noUpcomingEventsGroup.visibility = View.VISIBLE

            }

        })

    }
}