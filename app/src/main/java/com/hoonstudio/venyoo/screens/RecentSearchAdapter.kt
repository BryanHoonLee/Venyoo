package com.hoonstudio.venyoo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoonstudio.venyoo.databinding.ItemRecentSearchBinding
import com.hoonstudio.venyoo.venues.RecentSearch

class RecentSearchAdapter(
    private val itemClicked: (RecentSearch) -> Unit
): RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>() {

    private var recentSearchList: List<RecentSearch> = emptyList()

    fun bindData(recentSearch: List<RecentSearch>){
        recentSearchList = recentSearch
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        val binding = ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.bind(recentSearchList[position])
    }

    override fun getItemCount(): Int {
        return recentSearchList.size
    }

    inner class RecentSearchViewHolder(
        val binding: ItemRecentSearchBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(recentSearch: RecentSearch){
            binding.recentSearchTextView.text = recentSearch.name
            itemView.setOnClickListener {
                itemClicked(recentSearch)
            }
        }
    }
}