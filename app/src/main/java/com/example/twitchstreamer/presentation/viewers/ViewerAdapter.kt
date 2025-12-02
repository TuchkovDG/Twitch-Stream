package com.example.twitchstreamer.presentation.viewers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchstreamer.databinding.ItemViewerBinding

class ViewerAdapter : RecyclerView.Adapter<ViewerAdapter.ViewerViewHolder>() {

    private val items = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewerViewHolder {
        val binding = ItemViewerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewerViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewerViewHolder(
        private val binding: ItemViewerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) = with(binding) {
            tvViewerName.text = item
        }
    }
}