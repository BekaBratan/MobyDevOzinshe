package com.example.mobydevozinshe.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.data.model.CategoriesResponseItem
import com.example.mobydevozinshe.data.model.CategoryAgesResponseItem
import com.example.mobydevozinshe.databinding.ItemCardBinding
import com.example.mobydevozinshe.databinding.ItemCategoriesBinding
import com.example.mobydevozinshe.presentation.home.adapter.CategoryAgesAdapter
import com.example.mobydevozinshe.presentation.home.adapter.RcViewItemClickCategoryCallback

class SearchCategoryAdapter: RecyclerView.Adapter<SearchCategoryAdapter.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<CategoriesResponseItem>() {
        override fun areItemsTheSame(
            oldItem: CategoriesResponseItem,
            newItem: CategoriesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CategoriesResponseItem,
            newItem: CategoriesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<CategoriesResponseItem>){
        differ.submitList(list)
    }

    private var listenerClickAtItem: RcViewItemClickCategoryCallback? = null
    fun setOnCategoriesClickListener(listener: RcViewItemClickCategoryCallback) {
        this.listenerClickAtItem = listener
    }

    inner class MyViewHolder(private var binding: ItemCategoriesBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: CategoriesResponseItem){
            binding.tvCategory.text = item.name
            itemView.setOnClickListener {
                listenerClickAtItem?.onClick(item.id, item.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }
}