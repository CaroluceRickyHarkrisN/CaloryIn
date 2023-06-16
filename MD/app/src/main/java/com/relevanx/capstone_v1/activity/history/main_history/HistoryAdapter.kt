package com.relevanx.capstone_v1.activity.history.main_history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.relevanx.capstone_v1.activity.history.detail_history.DetailHistoryActivity
import com.relevanx.capstone_v1.api.HistoryResponseItem
import com.relevanx.capstone_v1.databinding.ItemFoodBinding

class HistoryAdapter(private val historyList: List<HistoryResponseItem>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(historyItem: HistoryResponseItem) {
            val maxCharacters = 10 // Jumlah maksimal karakter yang ingin ditampilkan
            val nameFoodLength = historyItem.nameFood?.length
            if (nameFoodLength != null) {
                binding.foodName.text = if (nameFoodLength > maxCharacters) {
                    historyItem.nameFood.substring(0, maxCharacters) + "..."
                } else {
                    historyItem.nameFood
                }
            }
            binding.foodNumber.text = historyItem.recordId
            binding.date.text = historyItem.dateRecord

            Glide.with(itemView)
                .load(historyItem.imageURL)
                .into(binding.foodImage)

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailHistoryActivity::class.java)
                intent.putExtra(DetailHistoryActivity.EXTRA_DATA, historyItem.recordId)
                itemView.context.startActivity(intent)
            }
        }
    }
}