package com.example.mobydevozinshe.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobydevozinshe.databinding.ItemMainMoviesBinding
import com.example.mobydevozinshe.data.model.MoviesItem

class MainMoviesAdapter: RecyclerView.Adapter<MainMoviesAdapter.MyViewHolder>() {

    private var moviesList = mutableListOf<MoviesItem>()

    inner class MyViewHolder(private var binding: ItemMainMoviesBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(movie: MoviesItem){
            binding.tvName.text = movie.name
            binding.tvCategory.text = movie.categories[0].name
            binding.tvDescription.text = movie.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemMainMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MoviesItem>){
        moviesList.clear()
        moviesList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(moviesList[position])
    }
}