package com.example.mobydevozinshe.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = SharedProvider(requireContext()).getToken()
        viewModel.getMovie(token, args.movieId)

        viewModel.movieResponseItem.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.poster.link)
                .into(binding.ivScreen)

            binding.tvTitle.text = it.name
            binding.tvDescription.text = it.description
            binding.tvInfo.text = getString(it.year, it.timing)
            binding.tvTextDirector.text = it.director
            binding.tvTextProducer.text = it.producer

        }

    }

}