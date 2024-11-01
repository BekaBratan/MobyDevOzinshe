package com.example.mobydevozinshe.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobydevozinshe.databinding.ItemCardShimmerBinding
import com.example.mobydevozinshe.databinding.ItemEpisodeShimmerBinding
import com.example.mobydevozinshe.databinding.ItemFavMoviesShimmerBinding
import com.example.mobydevozinshe.databinding.ItemImageShimmerBinding
import com.example.mobydevozinshe.databinding.ItemMainMoviesShimmerBinding
import com.example.mobydevozinshe.databinding.ItemPosterMoviesShimmerBinding

class ShimmerAdapter(val shimmerType: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<String>(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
    )

    inner class MainMoviesViewHolder(private var binding: ItemMainMoviesShimmerBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: String){
            binding.root.startShimmer()
        }
    }

    inner class PosterMoviesViewHolder(private var binding: ItemPosterMoviesShimmerBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: String){
            binding.root.startShimmer()
        }
    }

    inner class CardViewHolder(private var binding: ItemCardShimmerBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: String){
            binding.root.startShimmer()
        }
    }

    inner class EpisodeViewHolder(private var binding: ItemEpisodeShimmerBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: String){
            binding.root.startShimmer()
        }
    }

    inner class FavouriteViewHolder(private var binding: ItemFavMoviesShimmerBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: String){
            binding.root.startShimmer()
        }
    }

    inner class ImageViewHolder(private var binding: ItemImageShimmerBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: String){
            binding.root.startShimmer()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(shimmerType){
            1 -> MainMoviesViewHolder(ItemMainMoviesShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            2 -> CardViewHolder(ItemCardShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            3 -> EpisodeViewHolder(ItemEpisodeShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            4 -> FavouriteViewHolder(ItemFavMoviesShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            5 -> ImageViewHolder(ItemImageShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> PosterMoviesViewHolder(ItemPosterMoviesShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(shimmerType){
            1 -> (holder as MainMoviesViewHolder).onBind(list[position])
            2 -> (holder as CardViewHolder).onBind(list[position])
            3 -> (holder as EpisodeViewHolder).onBind(list[position])
            4 -> (holder as FavouriteViewHolder).onBind(list[position])
            5 -> (holder as ImageViewHolder).onBind(list[position])
            else -> (holder as PosterMoviesViewHolder).onBind(list[position])
        }
    }
}