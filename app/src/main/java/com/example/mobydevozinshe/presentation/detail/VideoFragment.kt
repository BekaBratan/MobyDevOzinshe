package com.example.mobydevozinshe.presentation.detail

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobydevozinshe.R
import com.example.mobydevozinshe.databinding.FragmentVideoBinding
import com.example.mobydevozinshe.provideNavigationHost
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.views.YouTubePlayerSeekBar
import kotlinx.coroutines.launch

class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding
    private val args: VideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        provideNavigationHost()?.apply {
            setNavigationVisability(false)
        }
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onStart() {
        super.onStart()
        provideNavigationHost()?.apply {
            setNavigationVisability(false)
        }
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch { moviePlay(args.videoLink) }
    }

    fun moviePlay(link: String) {
        val videoPlayer = binding.videoPlayer
        lifecycle.addObserver(videoPlayer)

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val defaultPlayerUiController = DefaultPlayerUiController(videoPlayer, youTubePlayer)
                defaultPlayerUiController.rootView.findViewById<View>(com.pierfrancescosoffritti.androidyoutubeplayer.R.id.drop_shadow_top).apply {
                    setBackgroundResource(R.drawable.ic_cross)
                    setPadding(24, 24, 24, 24)
                    updateLayoutParams {
                        width = 170
                        height = 170
                    }
                    setOnClickListener {
                        findNavController().popBackStack()
                    }
                }
                defaultPlayerUiController.showYouTubeButton(false)
                defaultPlayerUiController.showFullscreenButton(false)
                defaultPlayerUiController.showVideoTitle(false)
                videoPlayer.setCustomPlayerUi(defaultPlayerUiController.rootView)
                defaultPlayerUiController.rootView.findViewById<YouTubePlayerSeekBar>(com.pierfrancescosoffritti.androidyoutubeplayer.R.id.youtube_player_seekbar).apply {
                    setColor(resources.getColor(R.color.Red500, null))
                }
                youTubePlayer.loadOrCueVideo(lifecycle, link, 0f)
            }
        }

        val options: IFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(0)
            .build()
        videoPlayer.initialize(listener, options)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}