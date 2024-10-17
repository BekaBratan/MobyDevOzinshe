package com.example.mobydevozinshe.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.presentation.home.adapter.MainMoviesAdapter
import com.example.mobydevozinshe.databinding.FragmentHomeBinding
import com.example.mobydevozinshe.presentation.home.adapter.CategoryAgesAdapter
import com.example.mobydevozinshe.presentation.home.adapter.GenreAdapter
import com.example.mobydevozinshe.presentation.home.adapter.MainCategoryMoviesAdapter
import com.example.mobydevozinshe.presentation.home.adapter.RcViewItemClickIdCallback
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
        provideNavigationHost()?.apply { setNavigationVisibility(true) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.apply { setNavigationVisibility(true) }

        val token = SharedProvider(requireContext()).getToken()
        viewModel.getMainMovies(token)

        val adapterMainMovies = MainMoviesAdapter()
        binding.rcMainMovies.adapter = adapterMainMovies
        adapterMainMovies.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId))
            }
        })
        viewModel.mainMoviesResponse.observe(viewLifecycleOwner) {
            adapterMainMovies.submitList(it)
        }

        viewModel.getMainCategoryMovies(token)
        viewModel.getGenres(token)
        viewModel.getCategoryAges(token)

        viewModel.genresResponse.observe(viewLifecycleOwner) {
            val adapterGenres = GenreAdapter()
            adapterGenres.setOnGenreClickListener(object: RcViewItemClickIdCallback{
                override fun onClick(movieId: Int) {
                     findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(movieId, "genre"))
                }
            })
            binding.rcGenres.adapter = adapterGenres
            adapterGenres.submitList(it)
        }

        viewModel.categoryAgesResponse.observe(viewLifecycleOwner) {
            val adapterCategoryAges = CategoryAgesAdapter()
            adapterCategoryAges.setOnCategoryAgesClickListener(object: RcViewItemClickIdCallback{
                override fun onClick(movieId: Int) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(movieId, "ageCategory"))
                }
            })
            binding.rcCategoryAges.adapter = adapterCategoryAges
            adapterCategoryAges.submitList(it)
        }

        val adapterMainCategoryMovies1 = MainCategoryMoviesAdapter()
        adapterMainCategoryMovies1.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId))
            }
        })
        binding.rcMainCategoryMovies1.adapter = adapterMainCategoryMovies1
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            adapterMainCategoryMovies1.submitList(it[0].movies)
            binding.tvCategoryName1.text = it[0].categoryName
            val categoryId = it[0].categoryId
            binding.llCategoryName1.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "category"))

            }
        }

        val adapterMainCategoryMovies2 = MainCategoryMoviesAdapter()
        adapterMainCategoryMovies2.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId))
            }
        })
        binding.rcMainCategoryMovies2.adapter = adapterMainCategoryMovies2
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            adapterMainCategoryMovies2.submitList(it[1].movies)
            binding.tvCategoryName2.text = it[1].categoryName
            val categoryId = it[1].categoryId
            binding.llCategoryName2.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "category"))

            }
        }

        val adapterMainCategoryMovies3 = MainCategoryMoviesAdapter()
        adapterMainCategoryMovies3.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId))
            }
        })
        binding.rcMainCategoryMovies3.adapter = adapterMainCategoryMovies3
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            adapterMainCategoryMovies3.submitList(it[2].movies)
            binding.tvCategoryName3.text = it[2].categoryName
            val categoryId = it[2].categoryId
            binding.llCategoryName3.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "category"))

            }
        }

        val adapterMainCategoryMovies4 = MainCategoryMoviesAdapter()
        adapterMainCategoryMovies4.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId))
            }
        })
        binding.rcMainCategoryMovies4.adapter = adapterMainCategoryMovies4
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            adapterMainCategoryMovies4.submitList(it[3].movies)
            binding.tvCategoryName4.text = it[3].categoryName
            val categoryId = it[3].categoryId
            binding.llCategoryName4.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "category"))

            }
        }
    }
}