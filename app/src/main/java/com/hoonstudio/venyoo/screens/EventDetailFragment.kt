package com.hoonstudio.venyoo.screens

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import coil.load
import coil.transform.BlurTransformation
import com.hoonstudio.venyoo.Constants
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.databinding.FragmentEventDetailBinding
import com.hoonstudio.venyoo.networking.UrlProvider
import com.hoonstudio.venyoo.screens.common.fragments.BaseFragment
import com.hoonstudio.venyoo.screens.common.viewmodel.ViewModelFactory
import com.google.zxing.WriterException
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class EventDetailFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val eventViewModel: EventViewModel by navGraphViewModels(R.id.venue_navigation){ viewModelFactory}

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

    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        eventViewModel.currentEvent.observe(viewLifecycleOwner, Observer{ event ->

            val startTime = event.dates.start.dateTime
            val splitTime = startTime.split("T", "-", "Z", ":")
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            if(!startTime.isNullOrBlank()){
                calendar.set(splitTime[0].toInt(), splitTime[1].toInt() - 1, splitTime[2].toInt(), splitTime[3].toInt(), splitTime[4].toInt())
            }


            var attractions = event._embedded.attractions

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

            /** EVENT START TIME **/
            val time = Instant.ofEpochMilli(calendar.timeInMillis)
                .atZone(ZoneId.of(TimeZone.getDefault().id))
            var startHour = time.hour
            val startMinute = time.minute
            val amPmNumber = startHour/12
            startHour %= 12
            var amPm = "AM"
            if(amPmNumber == 0) amPm = "AM" else amPm = "PM"
            var startHourConverted = if(startHour.toString().length == 1) "0${startHour}" else "${startHour}"
            var startMinuteConverted = if(startMinute.toString().length == 1) "0${startMinute}" else "${startMinute}"

            if(!startTime.isNullOrBlank()) {
                val startTime = "${startHourConverted}:${startMinuteConverted} ${amPm}"
                if (event.dates.start.noSpecificTime || startTime.isNullOrEmpty()) {
                    binding.eventStartTimeTextView.text =
                        resources.getString(R.string.no_start_time_declared)
                } else {
                    binding.eventStartTimeTextView.text = startTime
                }
            }else{
                binding.eventStartTimeTextView.text =
                    resources.getString(R.string.no_start_time_declared)
            }

            /** Event URL QR CODE **/
            var eventUrl = ""
            if(event._embedded.attractions.isNotEmpty()){
                eventUrl = event._embedded.attractions[0].url
            }else{
                eventUrl = urlProvider.ticketMasterWebsiteUrl()
            }

            val qrgEncoder = QRGEncoder(eventUrl, null, QRGContents.Type.TEXT, 800)
            qrgEncoder.colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            qrgEncoder.colorBlack = ContextCompat.getColor(requireContext(), R.color.brand_primary_700)
            try{
                val qrBitmap = qrgEncoder.bitmap
                binding.qrCodeImageView.load(qrBitmap)
            } catch (e: WriterException){
                Log.e("EventDetailFragmentQRGen", e.toString())
            }

            /** Event Setlist Button **/
            binding.eventSetlistButton.setOnClickListener {
                if(!attractions.isNullOrEmpty()){
                    eventViewModel.fetchSetlist(attractions[0].name)
                }else{
                    eventViewModel.fetchSetlist(Constants.NULL_OR_EMPTY_VALUE)
                }
                navigateToSetlistBottomSheetDialogFragment()
            }

            /** Event Calendar Button **/
            binding.eventCalendarSaveButton.setOnClickListener {

                val startMillis = calendar.timeInMillis

                val calendarIntent = Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                    .putExtra(CalendarContract.Events.TITLE, "${event.name} Event")
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, eventViewModel.currentAddress)
                    .putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
                startActivity(calendarIntent)
            }

            /** Event Purchase Button **/
            binding.eventPurchaseTicketButton.setOnClickListener {
                var url = ""
                if(!attractions.isNullOrEmpty()){
                    url = attractions[0].url
                }
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

            /** Event Share Button **/
            binding.eventShareButton.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)

                val ticketView = binding.root
                val ticketViewBitmap = (Bitmap.createBitmap(ticketView.width, ticketView.height, Bitmap.Config.ARGB_8888))
                val canvas = Canvas(ticketViewBitmap)
                view.draw(canvas)
                val uri = convertBitmapToUri(ticketViewBitmap)

                shareIntent.setDataAndType(uri,"image/png")
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

                var url = ""
                if(!attractions.isNullOrEmpty()){
                    url = attractions[0].url
                }
                if(url.isNullOrBlank()){
                    url = "URL Unavailable"
                }
                shareIntent.putExtra(Intent.EXTRA_TEXT, url)

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "${event.name} Event Tickets")

                startActivity(Intent.createChooser(shareIntent, null))
            }

            /** EVENT DESCRIPTION **/
            val eventDescription = "${event.info} ${event.pleaseNote}"
            binding.eventInfoTextView.text = eventDescription
            binding.eventInfoTextView.movementMethod = ScrollingMovementMethod()

        })
    }

    private fun convertBitmapToUri(bitmap: Bitmap): Uri{
        val file = File(requireContext().cacheDir, "Venyoo")
        file.delete()
        file.createNewFile()
        val fileOutputStream = file.outputStream()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        fileOutputStream.write(byteArray)
        fileOutputStream.close()
        byteArrayOutputStream.close()

        val uri = FileProvider.getUriForFile(requireContext(), "com.hoonstudio.venyoo.screens.provider", file)
        return uri
    }

    private fun navigateToSetlistBottomSheetDialogFragment(){
        if (findNavController().currentDestination?.id != R.id.setlistBottomSheetDialogFragment) {
            findNavController().navigate(EventDetailFragmentDirections.actionEventDetailFragmentToSetlistBottomSheetDialogNavigation())
        }
    }
}










