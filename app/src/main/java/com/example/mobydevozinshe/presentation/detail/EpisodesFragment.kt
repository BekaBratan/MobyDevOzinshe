package com.example.mobydevozinshe.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentEpisodesBinding
import com.example.mobydevozinshe.presentation.CustomDividerItemDecoration
import com.example.mobydevozinshe.presentation.ShimmerAdapter
import com.example.mobydevozinshe.presentation.detail.adapter.EpisodesAdapter
import com.example.mobydevozinshe.presentation.detail.adapter.RcViewItemClickIdCallback
import com.example.mobydevozinshe.presentation.detail.adapter.RcViewItemClickLinkCallback
import com.example.mobydevozinshe.presentation.detail.adapter.SeasonsAdapter
import com.example.mobydevozinshe.provideNavigationHost

class EpisodesFragment : Fragment() {

    private lateinit var binding: FragmentEpisodesBinding
    private val args: EpisodesFragmentArgs by navArgs()
    private val viewModel: EpisodesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
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

        val token = SharedProvider(requireContext()).getToken()
        viewModel.getSeasons(token, args.movieId)

        val shimmerAdapter = ShimmerAdapter(3)
        binding.rcEpisodes.adapter = shimmerAdapter

        viewModel.seasonsResponseItem.observe(viewLifecycleOwner) {
            binding.run {
                toolbar.title.text = "Бөлімдер"
                toolbar.btnBack.setOnClickListener { findNavController().popBackStack() }

                val adapterSeason = SeasonsAdapter()
                adapterSeason.submitList(it)

                val adapterEpisode = EpisodesAdapter()
                adapterEpisode.submitList(it[0].videos)

                adapterSeason.setOnItemClickListener(object : RcViewItemClickIdCallback {
                    override fun onClick(id: Int) {
                        adapterEpisode.submitList(it[id-1].videos)
                    }
                })

                adapterEpisode.setOnItemClickListener(object : RcViewItemClickLinkCallback {
                    override fun onClick(link: String) {
                        findNavController().navigate(
                            EpisodesFragmentDirections.actionEpisodesFragmentToVideoFragment(link)
                        )
                    }
                })

                rcSeasons.adapter = adapterSeason
                rcEpisodes.adapter = adapterEpisode
                rcEpisodes.addItemDecoration(CustomDividerItemDecoration(getDrawable(requireContext(), R.drawable.divider_1dp_grey)!!))
            }
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(R.string.errorConnection), Toast.LENGTH_SHORT).show()
        }
    }

}