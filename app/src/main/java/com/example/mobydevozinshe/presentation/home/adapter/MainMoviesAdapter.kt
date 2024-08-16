package com.example.mobydevozinshe.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.data.model.MainMoviesResponseItem
import com.example.mobydevozinshe.databinding.ItemMainMoviesBinding

class MainMoviesAdapter: RecyclerView.Adapter<MainMoviesAdapter.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<MainMoviesResponseItem>() {
        override fun areItemsTheSame(
            oldItem: MainMoviesResponseItem,
            newItem: MainMoviesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MainMoviesResponseItem,
            newItem: MainMoviesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<MainMoviesResponseItem>){
        differ.submitList(list)
    }

    private var listenerClickAtItem: RcViewItemClickMainMoviesCallback? = null
    fun setOnMovieClickListener(listener: RcViewItemClickMainMoviesCallback) {
        this.listenerClickAtItem = listener
    }

    inner class MyViewHolder(private var binding: ItemMainMoviesBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: MainMoviesResponseItem){
            binding.tvName.text = item.movie.name
            binding.tvCategory.text = item.movie.categories[0].name
            binding.tvDescription.text = item.movie.description
            Glide.with(itemView.context)
                .load(item.link)
                .into(binding.ivScreen)
            itemView.setOnClickListener {
                listenerClickAtItem?.onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemMainMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }
}