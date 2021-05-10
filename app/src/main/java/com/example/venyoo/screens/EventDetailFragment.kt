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
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import coil.load
import coil.transform.BlurTransformation
import com.example.venyoo.R
import com.example.venyoo.databinding.FragmentEventDetailBinding
import com.example.venyoo.networking.UrlProvider
import com.example.venyoo.screens.common.fragments.BaseFragment
import com.example.venyoo.screens.common.viewmodel.ViewModelFactory
import com.google.zxing.WriterException
import javax.inject.Inject

class EventDetailFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val eventViewModel: EventViewModel by navGraphViewModels(R.id.event_navigation){ viewModelFactory}

    private lateinit var binding: FragmentEventDetailBinding

    @Inject
    lateinit var urlProvider: UrlProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventDetailBinding.inflate(inflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventViewModel.currentEvent.observe(viewLifecycleOwner, Observer{ event ->

            /** EVENT IMAGE **/
            binding.eventImageView.load(event.images[0].url){
                transformations(BlurTransformation(requireContext(), 10f))
            }

            /** EVENT NAME **/
            binding.attractionNameTextView.text = event.name

            /** EVENT DATE **/
            val convertedDate = ticketMasterLocalDateConverter(event.dates.start.localDate)
            binding.eventMonthTextView.text = convertedDate.month
            binding.eventDayTextView.text = convertedDate.day

//            /** EVENT STATUS **/
//            val eventStatus = event.dates.status.code
//            if(eventStatus != null) {
//                binding.eventStatusTextView.text = eventStatus.codeName
//            }else{
//                binding.eventStatusTextView.text = ""
//            }

            /** EVENT START TIME **/
            val startTime = event.dates.start.dateTime
            if(!startTime.isNullOrBlank()) {
                val splitTime = startTime.split("T")
                val timeIn24HourFormat = "${splitTime[1].dropLast(1)} UTC"
//            val splitTimeIn24HourFormat = timeIn24HourFormat.split(":")
//            val hourConvertedToPST = splitTimeIn24HourFormat[0].toInt() - 8
                if (event.dates.start.noSpecificTime || startTime.isNullOrEmpty()) {
                    binding.eventStartTimeTextView.text =
                        resources.getString(R.string.no_start_time_declared)
                } else {
                    binding.eventStartTimeTextView.text = timeIn24HourFormat
                }
            }else{
                binding.eventStartTimeTextView.text =
                    resources.getString(R.string.no_start_time_declared)
            }

            /** Event URL QR CODE **/
            val qrgEncoder = QRGEncoder(event._embedded.attractions[0].url, null, QRGContents.Type.TEXT, 800)
            Log.d("TEST", event._embedded.attractions[0].url)
            qrgEncoder.colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            qrgEncoder.colorBlack = ContextCompat.getColor(requireContext(), R.color.brand_primary_700)
            try{
                val qrBitmap = qrgEncoder.bitmap
                binding.qrCodeImageView.load(qrBitmap)
            } catch (e: WriterException){
                Log.v("EventDetailFragmentQRGen", e.toString())
            }

            /** Event Price Range **/
            if(!event.priceRanges.isNullOrEmpty()) {
                val range = event.priceRanges[0]
                if (range.min == null || range.max == null) {
                    binding.eventPriceTextView.text = "Price TBA"
                } else {
                    if(range.min % 1.0 == 0.0){
                        binding.eventPriceTextView.text = "$${range.min.toInt()} - $${range.max.toInt()}"
                    }else{
                        binding.eventPriceTextView.text = "$${range.min}0 - $${range.max}0"
                    }
                }
            }else{
                binding.eventPriceTextView.text = "Price TBA"
            }

            /** Event Purchase Button **/
            binding.eventPurchaseTicketButton.setOnClickListener {
                var url = event._embedded.attractions[0].url
                if(url.isNullOrBlank()){
                    url = urlProvider.ticketMasterWebsiteUrl()
                }
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

            /** EVENT DESCRIPTION **/
            val eventDescription = "${event.info} ${event.pleaseNote}"
            binding.eventInfoTextView.text = eventDescription
            var eventDescriptionExpanded= false
            binding.eventInfoTextView.setOnClickListener {
                eventDescriptionExpanded = !eventDescriptionExpanded
                if(eventDescriptionExpanded){
                    binding.eventInfoTextView.maxLines = 50
                }else{
                    binding.eventInfoTextView.maxLines = 3
                }
            }
        })

    }
}










