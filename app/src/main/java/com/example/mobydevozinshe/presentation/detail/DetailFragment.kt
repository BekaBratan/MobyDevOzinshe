package com.example.mobydevozinshe.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.data.SharedProvider
import com.example.mobydevozinshe.databinding.FragmentDetailBinding
import com.example.mobydevozinshe.provideNavigationHost

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

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply {
            setNavigationVisability(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = SharedProvider(requireContext()).getToken()
        viewModel.getMovie(token, args.movieId)

        viewModel.movieResponseItem.observe(viewLifecycleOwner) {
            binding.run {
                btnBack.setOnClickListener {
                    findNavController().navigateUp()
                }

                Glide.with(requireContext())
                    .load(it.poster.link)
                    .into(ivScreen)

                tvTitle.text = it.name
                var addInfo = it.year.toString()
                for (genre in it.genres) {
                    addInfo += " · ${genre.name}"
                }
                addInfo += " · ${it.seasonCount} сезон, ${it.seriesCount} серия, ${it.timing} мин."
                tvInfo.text = addInfo

                tvDescription.text = it.description
                if (tvDescription.lineCount > 3) {
                    tvDescription.maxLines = 7
                    tvMoreDescription.text = getString(R.string.more_description)
                    layoutDescription.setFadeEdges(false, false, true, false)
                    tvMoreDescription.visibility = View.VISIBLE
                    tvMoreDescription.setOnClickListener {
                        if (tvDescription.maxLines < 8) {
                            tvDescription.maxLines = Int.MAX_VALUE
                            tvMoreDescription.text = getString(R.string.hide_description)
                            layoutDescription.setFadeEdges(false, false, false, false)
                        } else {
                            tvDescription.maxLines = 7
                            tvMoreDescription.text = getString(R.string.more_description)
                            layoutDescription.setFadeEdges(false, false, true, false)
                        }
                    }
                } else {
                    layoutDescription.setFadeEdges(false, false, false, false)
                    tvMoreDescription.visibility = View.GONE
                }

                tvTextDirector.text = it.director
                tvTextProducer.text = it.producer

                if (it.movieType == "SERIAL") {
                    tvTextEpisodes.text = "${it.seasonCount} сезон, ${it.seriesCount} серия"
                    layoutEpisodes.setOnClickListener {
                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToEpisodesFragment(args.movieId))
                    }
                } else {
                    layoutEpisodes.visibility = View.GONE
                }

                val adapterScreenshot = ImageAdapter()
                adapterScreenshot.submitList(it.screenshots)
                adapterScreenshot.setOnImageClickListener(object : RcViewItemClickLinkCallback {
                    override fun onClick(link: String) {
                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToImageFragment(link)                        )
                    }
                })
                rcScreenshots.adapter = adapterScreenshot

                btnPlay.setOnClickListener { click ->
                    if (it.video != null) {
                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToVideoFragment(it.video.link))
                    } else {
                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToEpisodesFragment(args.movieId))
                    }
                }
            }
        }

    }

}