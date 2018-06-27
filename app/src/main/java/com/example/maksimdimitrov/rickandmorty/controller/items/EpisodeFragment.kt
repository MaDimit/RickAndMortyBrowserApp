package com.example.maksimdimitrov.rickandmorty.controller.items


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.model.Model
import java.util.*

typealias Episode = Model.Episode

class EpisodeFragment : ItemFragment(){

    private val TAG = LocationFragment::class.qualifiedName

    private lateinit var episode: Episode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getParcelable<Episode>(EPISODE)?.let {
                episode = it
                Log.d(TAG, "Episode initialized: $episode")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_episode, container, false)
        return rootView
    }


    companion object {
        private const val EPISODE = "episode"
        @JvmStatic
        fun newInstance(episode: Episode) =
                EpisodeFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(EPISODE, episode)
                    }
                }
    }

}
