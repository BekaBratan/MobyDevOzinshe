package com.example.mobydevozinshe.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.presentation.home.adapter.MainMoviesAdapter
import com.example.mobydevozinshe.databinding.FragmentHomeBinding
import com.example.mobydevozinshe.presentation.home.adapter.CategoryAgesAdapter
import com.example.mobydevozinshe.presentation.home.adapter.GenreAdapter
import com.example.mobydevozinshe.presentation.home.adapter.MainCategoryMoviesAdapter
import com.example.mobydevozinshe.presentation.home.adapter.RcViewItemClickCategoryCallback
import com.example.mobydevozinshe.presentation.home.adapter.RcViewItemClickIdCallback
import com.example.mobydevozinshe.presentation.ShimmerAdapter
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

    override fun onPause() {
        super.onPause()
        viewModel.scrollPosition.value = binding.root.scrollY
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.apply { setNavigationVisibility(true) }

        viewModel.scrollPosition.observe(viewLifecycleOwner) { position ->
            if (position != null) {
                binding.root.post {
                    binding.root.scrollY = position
                }
            }
        }

        val navOptions = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true).build()

        val token = SharedProvider(requireContext()).getToken()
        viewModel.getMainMovies(token)

        val shimmerMainAdapter = ShimmerAdapter(1)
        val shimmerCardAdapter = ShimmerAdapter(2)
        val shimmerPosterAdapter = ShimmerAdapter(0)

        val adapterMainMovies = MainMoviesAdapter()
        binding.rcMainMovies.adapter = shimmerMainAdapter
        adapterMainMovies.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId), navOptions)
            }
        })
        viewModel.mainMoviesResponse.observe(viewLifecycleOwner) {
            adapterMainMovies.submitList(it)
            binding.rcMainMovies.adapter = adapterMainMovies
        }

        viewModel.getMainCategoryMovies(token)
        viewModel.getGenres(token)
        viewModel.getCategoryAges(token)

        binding.rcGenres.adapter = shimmerCardAdapter
        viewModel.genresResponse.observe(viewLifecycleOwner) {
            val adapterGenres = GenreAdapter()
            adapterGenres.setOnGenreClickListener(object: RcViewItemClickCategoryCallback{
                override fun onClick(categoryId: Int, categoryName: String) {
                     findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "genre", categoryName), navOptions)
                }
            })
            binding.rcGenres.adapter = adapterGenres
            adapterGenres.submitList(it)
        }

        binding.rcCategoryAges.adapter = shimmerCardAdapter
        viewModel.categoryAgesResponse.observe(viewLifecycleOwner) {
            val adapterCategoryAges = CategoryAgesAdapter()
            adapterCategoryAges.setOnCategoryAgesClickListener(object:
                RcViewItemClickCategoryCallback {
                override fun onClick(categoryId: Int, categoryName: String) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "ageCategory", categoryName), navOptions)
                }
            })
            binding.rcCategoryAges.adapter = adapterCategoryAges
            adapterCategoryAges.submitList(it)
        }

        val adapterMainCategoryMovies1 = MainCategoryMoviesAdapter()
        adapterMainCategoryMovies1.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId), navOptions)
            }
        })
        binding.rcMainCategoryMovies1.adapter = shimmerPosterAdapter
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            binding.rcMainCategoryMovies1.adapter = adapterMainCategoryMovies1
            adapterMainCategoryMovies1.submitList(it[0].movies)
            val categoryName = it[0].categoryName
            binding.tvCategoryName1.text = categoryName
            val categoryId = it[0].categoryId
            binding.llCategoryName1.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "category", categoryName), navOptions)

            }
        }

        val adapterMainCategoryMovies2 = MainCategoryMoviesAdapter()
        adapterMainCategoryMovies2.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId), navOptions)
            }
        })
        binding.rcMainCategoryMovies2.adapter = shimmerPosterAdapter
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            binding.rcMainCategoryMovies2.adapter = adapterMainCategoryMovies2
            adapterMainCategoryMovies2.submitList(it[1].movies)
            val categoryName = it[1].categoryName
            binding.tvCategoryName2.text = categoryName
            val categoryId = it[1].categoryId
            binding.llCategoryName2.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "category", categoryName), navOptions)

            }
        }

        val adapterMainCategoryMovies3 = MainCategoryMoviesAdapter()
        adapterMainCategoryMovies3.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId), navOptions)
            }
        })
        binding.rcMainCategoryMovies3.adapter = shimmerPosterAdapter
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            binding.rcMainCategoryMovies3.adapter = adapterMainCategoryMovies3
            adapterMainCategoryMovies3.submitList(it[2].movies)
            val categoryName = it[2].categoryName
            binding.tvCategoryName3.text = categoryName
            val categoryId = it[2].categoryId
            binding.llCategoryName3.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "category", categoryName), navOptions)

            }
        }

        val adapterMainCategoryMovies4 = MainCategoryMoviesAdapter()
        adapterMainCategoryMovies4.setOnMovieClickListener(object: RcViewItemClickIdCallback{
            override fun onClick(movieId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId), navOptions)
            }
        })
        binding.rcMainCategoryMovies4.adapter = shimmerPosterAdapter
        viewModel.mainCategoryMoviesResponse.observe(viewLifecycleOwner) {
            binding.rcMainCategoryMovies4.adapter = adapterMainCategoryMovies4
            adapterMainCategoryMovies4.submitList(it[3].movies)
            val categoryName = it[3].categoryName
            binding.tvCategoryName4.text = categoryName
            val categoryId = it[3].categoryId
            binding.llCategoryName4.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId, "category", categoryName), navOptions)

            }
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(R.string.errorConnection), Toast.LENGTH_SHORT).show()
        }
    }
}