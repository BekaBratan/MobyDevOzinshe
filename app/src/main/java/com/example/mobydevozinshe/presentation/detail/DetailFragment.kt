package com.example.mobydevozinshe.presentation.detail

import android.os.Bundle
import android.util.Log
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
import com.example.mobydevozinshe.data.model.MovieIdModel
import com.example.mobydevozinshe.databinding.FragmentDetailBinding
import com.example.mobydevozinshe.presentation.detail.adapter.ImageAdapter
import com.example.mobydevozinshe.presentation.detail.adapter.RcViewItemClickIdCallback
import com.example.mobydevozinshe.presentation.detail.adapter.RcViewItemClickLinkCallback
import com.example.mobydevozinshe.presentation.detail.adapter.SimilarAdapter
import com.example.mobydevozinshe.provideNavigationHost

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private var favouriteState = false

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
            setNavigationVisibility(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = SharedProvider(requireContext()).getToken()
        viewModel.getMovie(token, args.movieId)
        viewModel.getSimilarMovies(token, args.movieId)

        viewModel.movieResponseItem.observe(viewLifecycleOwner) {
            binding.run {
                btnBack.setOnClickListener {
                    findNavController().navigateUp()
                }

                Glide.with(requireContext())
                    .load(it.poster.link)
                    .into(ivScreen)

                if (it.favorite) {
                    favouriteState = true
                    btnFavourite.setBackgroundResource(R.drawable.ic_bookmark_filled)
                } else {
                    favouriteState = false
                    btnFavourite.setBackgroundResource(R.drawable.ic_bookmark_outline)
                }

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
                    llDescription.setFadeEdges(false, false, true, false)
                    tvMoreDescription.visibility = View.VISIBLE
                    tvMoreDescription.setOnClickListener {
                        if (tvDescription.maxLines < 8) {
                            tvDescription.maxLines = Int.MAX_VALUE
                            tvMoreDescription.text = getString(R.string.hide_description)
                            llDescription.setFadeEdges(false, false, false, false)
                        } else {
                            tvDescription.maxLines = 7
                            tvMoreDescription.text = getString(R.string.more_description)
                            llDescription.setFadeEdges(false, false, true, false)
                        }
                    }
                } else {
                    llDescription.setFadeEdges(false, false, false, false)
                    tvMoreDescription.visibility = View.GONE
                }

                tvTextDirector.text = it.director
                tvTextProducer.text = it.producer

                if (it.movieType == "SERIAL") {
                    tvTextEpisodes.text = "${it.seasonCount} сезон, ${it.seriesCount} серия"
                    llEpisodes.setOnClickListener {
                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToEpisodesFragment(args.movieId))
                    }
                } else {
                    llEpisodes.visibility = View.GONE
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

                btnFavourite.setOnClickListener { click ->
                    if (favouriteState) {
                        viewModel.deleteFavourite(token, MovieIdModel(args.movieId))
                    } else {
                        viewModel.addFavourite(token, MovieIdModel(args.movieId))
                    }
                }
            }
        }

        viewModel.favouriteState.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnFavourite.setBackgroundResource(R.drawable.ic_bookmark_filled)
            } else {
                binding.btnFavourite.setBackgroundResource(R.drawable.ic_bookmark_outline)
            }
            favouriteState = it
        }

        viewModel.similarMoviesResponse.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.llSimilar.visibility = View.GONE
                binding.rcSameMovies.visibility = View.GONE
            } else {
                val adapter = SimilarAdapter()
                adapter.submitList(it)
                adapter.setOnMovieClickListener(object : RcViewItemClickIdCallback {
                    override fun onClick(id: Int) {
                        findNavController().navigate(
                            DetailFragmentDirections.actionDetailFragmentSelf(
                                id
                            )
                        )
                    }
                })
                binding.rcSameMovies.adapter = adapter
            }
        }

        binding.llSimilar.setOnClickListener {
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToCategoryFragment(args.movieId, "similar", getString(R.string.similar)))
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            Log.d("BBBB", it)
        }
    }

}