package com.example.mobydevozinshe.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.data.model.Video
import com.example.mobydevozinshe.databinding.ItemEpisodeBinding

class EpisodesAdapter: RecyclerView.Adapter<EpisodesAdapter.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(
            oldItem: Video,
            newItem: Video
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Video,
            newItem: Video
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Video>){
        differ.submitList(list)
    }

    private var listenerClickAtItem: RcViewItemClickLinkCallback? = null
    fun setOnItemClickListener(listener: RcViewItemClickLinkCallback) {
        this.listenerClickAtItem = listener
    }

    inner class MyViewHolder(private var binding: ItemEpisodeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Video) {
            binding.run {
                Glide.with(itemView.context)
                    .load("http://img.youtube.com/vi/${item.link}/maxresdefault.jpg")
                    .into(imgEpisode)
                tvEpisode.text = "${item.number}-ші бөлім"
            }
            itemView.setOnClickListener {
                listenerClickAtItem?.onClick(item.link)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }
}