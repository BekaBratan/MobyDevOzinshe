package com.example.mobydevozinshe.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobydevozinshe.data.model.SeasonsResponseItem
import com.example.mobydevozinshe.databinding.ItemSeasonBinding

class SeasonsAdapter: RecyclerView.Adapter<SeasonsAdapter.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<SeasonsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: SeasonsResponseItem,
            newItem: SeasonsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SeasonsResponseItem,
            newItem: SeasonsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<SeasonsResponseItem>){
        differ.submitList(list)
    }

    private var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnItemClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class MyViewHolder(private var binding: ItemSeasonBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: SeasonsResponseItem) {
            binding.run {
                tvSeason.text = "${item.number} сезон"
            }
            itemView.setOnClickListener {
                listenerClickAtItem?.onClick(item.number)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }
}