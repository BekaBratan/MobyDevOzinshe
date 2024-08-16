package com.example.mobydevozinshe.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.mobydevozinshe.data.adapter.MainMoviesAdapter
import com.example.mobydevozinshe.databinding.FragmentHomeBinding
import com.example.mobydevozinshe.data.repository.Repository
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterMainMovies = MainMoviesAdapter()

        binding.rcMainMovies.adapter = adapterMainMovies

        lifecycleScope.launch {
            runCatching { repository.getMainMovies(getToken()) }
                .fold(
                    onSuccess = {
                        adapterMainMovies.submitList(it[0].movies)
                        Log.d("BBBB", "Success getting movies \n $it")
                        Log.d("BBBB", getToken())
                    },
                    onFailure = {
                        Log.e("BBBB", "Fail getting movies $it")
                    }
                )
        }
    }

    private fun getToken(): String{
        val sharedPref = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        return "${sharedPref.getString("TokenType", "")} ${sharedPref.getString("AccessToken", "")}"
    }
}