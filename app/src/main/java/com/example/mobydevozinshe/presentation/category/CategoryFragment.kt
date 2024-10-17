package com.example.mobydevozinshe.presentation.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentCategoryBinding
import com.example.mobydevozinshe.presentation.CustomDividerItemDecoration
import com.example.mobydevozinshe.presentation.category.adapter.CategoryAdapter
import com.example.mobydevozinshe.provideNavigationHost

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()
    private val args: CategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
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
        binding.toolbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val token = SharedProvider(requireContext()).getToken()
        if (args.categoryType == "genre") {
            viewModel.getGenrePage(token, args.categoryId)
        } else if (args.categoryType == "category") {
            viewModel.getCategoryPage(token, args.categoryId)
        } else if (args.categoryType == "similar") {
            viewModel.getSimilarMovies(token, args.categoryId)
        } else {
            viewModel.getAgeCategoryPage(token, args.categoryId)
        }

        viewModel.moviesPageResponse.observe(viewLifecycleOwner) {
            val adapter = CategoryAdapter()
            binding.rvFavMovies.adapter = adapter

            adapter.submitList(it.content)
            binding.toolbar.title.text = args.categoryName
        }

        viewModel.similarMoviesResponse.observe(viewLifecycleOwner) {
            val adapter = CategoryAdapter()
            binding.rvFavMovies.adapter = adapter

            adapter.submitList(it)
            binding.toolbar.title.text = args.categoryName
        }

        binding.rvFavMovies.addItemDecoration(CustomDividerItemDecoration(getDrawable(requireContext(), R.drawable.divider_1dp_grey)!!))

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            binding.toolbar.title.text = it
        }
    }

}