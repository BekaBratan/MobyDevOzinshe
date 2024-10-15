package com.example.mobydevozinshe.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.data.model.CategoryAgesResponseItem
import com.example.mobydevozinshe.databinding.ItemCardBinding

class CategoryAgesAdapter: RecyclerView.Adapter<CategoryAgesAdapter.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<CategoryAgesResponseItem>() {
        override fun areItemsTheSame(
            oldItem: CategoryAgesResponseItem,
            newItem: CategoryAgesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CategoryAgesResponseItem,
            newItem: CategoryAgesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<CategoryAgesResponseItem>){
        differ.submitList(list)
    }

    private var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnCategoryAgesClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class MyViewHolder(private var binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: CategoryAgesResponseItem){
            binding.tvCard.text = item.name
            Glide.with(itemView.context)
                .load(item.link)
                .into(binding.imgCard)
            itemView.setOnClickListener {
                listenerClickAtItem?.onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }
}