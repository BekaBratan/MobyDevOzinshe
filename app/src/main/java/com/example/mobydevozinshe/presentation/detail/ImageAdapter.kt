package com.example.mobydevozinshe.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.data.model.Screenshot
import com.example.mobydevozinshe.databinding.ItemImageBinding

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Screenshot>() {
        override fun areItemsTheSame(
            oldItem: Screenshot,
            newItem: Screenshot
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Screenshot,
            newItem: Screenshot
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Screenshot>){
        differ.submitList(list)
    }

    private var listenerClickAtItem: RcViewItemClickImageCallback? = null
    fun setOnImageClickListener(listener: RcViewItemClickImageCallback) {
        this.listenerClickAtItem = listener
    }

    inner class MyViewHolder(private var binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Screenshot) {
            binding.run {
                Glide.with(itemView.context)
                    .load(item.link)
                    .into(ivScreenshot)
            }
            itemView.setOnClickListener {
                listenerClickAtItem?.onClick(item.link)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }
}