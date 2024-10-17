package com.example.mobydevozinshe.presentation.category.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.data.model.Content
import com.example.mobydevozinshe.databinding.ItemFavMoviesBinding
import com.example.mobydevozinshe.presentation.home.adapter.RcViewItemClickIdCallback

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(
            oldItem: Content,
            newItem: Content
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Content,
            newItem: Content
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Content>){
        differ.submitList(list)
    }

    private var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnFavouriteClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class MyViewHolder(private var binding: ItemFavMoviesBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(item: Content){
            binding.tvName.text = item.name
            var genres = ""
            item.genres.forEach {
                genres += it.name + " · "
            }
            binding.tvDescription.text = "${item.year} · $genres"
            Glide.with(itemView.context)
                .load(item.poster.link)
                .into(binding.ivScreen)
            itemView.setOnClickListener {
                listenerClickAtItem?.onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFavMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }
}