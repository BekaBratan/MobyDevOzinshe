package com.example.mobydevozinshe.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentSearchBinding
import com.example.mobydevozinshe.presentation.CustomDividerItemDecoration
import com.example.mobydevozinshe.presentation.home.adapter.RcViewItemClickCategoryCallback
import com.example.mobydevozinshe.presentation.home.adapter.RcViewItemClickIdCallback
import com.example.mobydevozinshe.presentation.search.adapter.SearchCategoryAdapter
import com.example.mobydevozinshe.presentation.search.adapter.SearchResultAdapter
import com.example.mobydevozinshe.provideNavigationHost

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = SharedProvider(requireContext()).getToken()

        viewModel.getCategories(token)

        binding.run {
            toolbar.btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            toolbar.title.text = getString(R.string.search)

            viewModel.categoriesResponse.observe(viewLifecycleOwner) {
                val adapter = SearchCategoryAdapter()
                adapter.setOnCategoriesClickListener(object : RcViewItemClickCategoryCallback {
                    override fun onClick(categoryId: Int, categoryName: String) {
                        findNavController().navigate(
                            SearchFragmentDirections.actionSearchFragmentToCategoryFragment(
                                categoryId,
                                "category",
                                categoryName
                            )
                        )
                    }
                })
                rvCategories.adapter = adapter
                adapter.submitList(it)
            }

            viewModel.errorResponse.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
            }

            etSearch.addTextChangedListener {
                if (it.toString().isNotEmpty()) {
                    btnSearch.isClickable = true
                    btnSearch.setImageResource(R.drawable.ic_search_outline_active)
                    setDrawableEnd(etSearch)
                } else {
                    btnSearch.isClickable = false
                    btnSearch.setImageResource(R.drawable.ic_search_outline)
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }
            }

            etSearch.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP && etSearch.compoundDrawables[2] != null) {
                    val drawableEnd = etSearch.compoundDrawables[2]
                    if (event.rawX >= (etSearch.right - drawableEnd.bounds.width())) {
                        clearText(etSearch)
                        return@setOnTouchListener true
                    }
                }
                false
            }

            btnSearch.setOnClickListener {
                val query = etSearch.text.toString()
                viewModel.searchMovies(token, query)
            }

            viewModel.searchResponse.observe(viewLifecycleOwner) {
                rvCategories.visibility = View.GONE
                if (it.isEmpty()) {
                    tvCategories.text = getString(R.string.nothing_found)
                } else {
                    tvCategories.text = getString(R.string.search_result)
                    val adapter = SearchResultAdapter()
                    rvSearchResults.adapter = adapter
                    adapter.setOnFavouriteClickListener(object : RcViewItemClickIdCallback {
                        override fun onClick(id: Int) {
                            findNavController().navigate(
                                SearchFragmentDirections.actionSearchFragmentToDetailFragment(id)
                            )
                        }
                    })
                    adapter.submitList(it)
                    svSearchResults.visibility = View.VISIBLE
                }
            }

            rvSearchResults.addItemDecoration(CustomDividerItemDecoration(getDrawable(requireContext(), R.drawable.divider_1dp_grey)!!))
        }
    }

    private fun clearText(editText: EditText) {
        editText.text.clear()
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    private fun setDrawableEnd(editText: EditText) {
        val drawableEnd = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search_cross)
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableEnd, null)
    }
}