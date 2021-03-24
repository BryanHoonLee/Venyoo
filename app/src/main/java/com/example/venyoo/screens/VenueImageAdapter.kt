package com.example.venyoo.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.example.venyoo.R
import com.example.venyoo.databinding.ItemVenueDetailImageBinding
import com.example.venyoo.venues.TicketMasterVenueImage

class VenueImageAdapter(
    private val onItemClicked: (TicketMasterVenueImage) -> Unit
) : RecyclerView.Adapter<VenueImageAdapter.VenueImageViewHolder>() {
    private var imageList: List<TicketMasterVenueImage> = emptyList()

    fun bindData(images: List<TicketMasterVenueImage>) {
        imageList = ArrayList(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VenueImageViewHolder {
        val binding =
            ItemVenueDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VenueImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VenueImageViewHolder, position: Int) {
//            if(imageList.isNotEmpty()){
//                Log.d("TEST123", "IMAGE")
//                holder.binding.venueImageView.load(imageList[position].url){
//                    Log.d("TEST123", "${position}: ${imageList[position].url}")
//                    listener(onSuccess = { request: ImageRequest, metadata: ImageResult.Metadata ->
//                        Log.d("TEST123", "SUCCESS")
//                        holder.binding.progressCircular.visibility = View.GONE
//                    }, onCancel = {
//                        Log.d("TEST123", "CANCEL")
//                    }, onError = { request: ImageRequest, throwable: Throwable ->
//                        Log.d("TEST123", "${throwable.stackTrace}")
//                    })
//                }
//            }else{
//                Log.d("TEST123", "NO IMAGE")
//                holder.binding.venueImageView.load(R.drawable.ic_launcher_foreground)
//                holder.binding.progressCircular.visibility = View.GONE
//            }
        holder.bind(imageList[position])

//        holder.itemView.setOnClickListener {
//            onItemClicked(imageList[position])
//        }


    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class VenueImageViewHolder(
        val binding: ItemVenueDetailImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: TicketMasterVenueImage){
            if(imageList.isNotEmpty()){
                binding.venueImageView.load(image.url){
                    Log.d("TEST123", "ahhh: ${image.url}")
                    listener(onSuccess = { request: ImageRequest, metadata: ImageResult.Metadata ->
                        binding.progressCircular.visibility = View.GONE
                    }, onCancel = {
                    }, onError = { request: ImageRequest, throwable: Throwable ->
                        Log.d("TEST123", "${throwable.stackTrace}")
                    })
                }
            }else{
                binding.venueImageView.load(R.drawable.ic_launcher_foreground)
                binding.progressCircular.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onItemClicked(image)
            }
        }
    }
}