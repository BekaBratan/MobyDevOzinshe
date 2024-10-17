package com.example.mobydevozinshe.presentation.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentFavouriteBinding
import com.example.mobydevozinshe.presentation.CustomDividerItemDecoration
import com.example.mobydevozinshe.presentation.category.CategoryFragmentDirections
import com.example.mobydevozinshe.presentation.favourite.adapter.FavouriteMoviesAdapter
import com.example.mobydevozinshe.presentation.home.adapter.RcViewItemClickIdCallback
import com.example.mobydevozinshe.provideNavigationHost

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
        }

        binding.toolbar.btnBack.visibility = View.GONE
        binding.toolbar.title.text = getString(R.string.list)

        val token = SharedProvider(requireContext()).getToken()
        viewModel.getFavouriteMovies(token)

        viewModel.favouriteMoviesResponse.observe(viewLifecycleOwner) {
            val adapterFavouriteMovies = FavouriteMoviesAdapter()
            binding.rvFavMovies.adapter = adapterFavouriteMovies

            adapterFavouriteMovies.setOnFavouriteClickListener(object: RcViewItemClickIdCallback {
                override fun onClick(movieId: Int) {
                    findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(movieId))
                }
            })
            adapterFavouriteMovies.submitList(it)
        }

        binding.rvFavMovies.addItemDecoration(CustomDividerItemDecoration(getDrawable(requireContext(), R.drawable.divider_1dp_grey)!!))

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            binding.toolbar.title.text = it
        }
    }

}