package com.example.mobydevozinshe.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.data.model.MoviesResponseItem
import com.example.mobydevozinshe.databinding.ItemPosterMoviesBinding

class SimilarAdapter: RecyclerView.Adapter<SimilarAdapter.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<MoviesResponseItem>() {
        override fun areItemsTheSame(
            oldItem: MoviesResponseItem,
            newItem: MoviesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MoviesResponseItem,
            newItem: MoviesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<MoviesResponseItem>){
        differ.submitList(list)
    }

    private var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnMovieClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class MyViewHolder(private var binding: ItemPosterMoviesBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: MoviesResponseItem){
            binding.tvName.text = item.name
            binding.tvCategory.text = item.categories.last().name
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
            ItemPosterMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }
}