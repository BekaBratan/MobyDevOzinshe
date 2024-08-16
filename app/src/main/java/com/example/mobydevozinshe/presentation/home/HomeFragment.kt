package com.example.mobydevozinshe.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.presentation.home.adapter.MainMoviesAdapter
import com.example.mobydevozinshe.databinding.FragmentHomeBinding
import com.example.mobydevozinshe.presentation.home.adapter.MainCategoryMoviesAdapter
import com.example.mobydevozinshe.provideNavigationHost

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply { setNavigationVisability(true) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.apply { setNavigationVisability(true) }

        val token = SharedProvider(requireContext()).getToken()
        viewModel.getMainMovies(token)

        val adapterMainMovies = MainMoviesAdapter()
        binding.rcMainMovies.adapter = adapterMainMovies
        viewModel.mainMoviesResponse.observe(viewLifecycleOwner) {
            adapterMainMovies.submitList(it)
        }

        viewModel.getMainCategoryMovies(token)
        val adapterMainCategoryMovies = MainCategoryMoviesAdapter()
        binding.rcMainCategoryMovies.adapter = adapterMainCategoryMovies
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            adapterMainCategoryMovies.submitList(it[0].movies)
        }
    }
}