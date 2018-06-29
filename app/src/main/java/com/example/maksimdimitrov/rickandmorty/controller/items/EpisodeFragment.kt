package com.example.maksimdimitrov.rickandmorty.controller.items


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.model.Model
import kotlinx.android.synthetic.main.fragment_episode.*
import java.util.*

typealias Episode = Model.Episode

class EpisodeFragment : ItemFragment() {

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
        initView()
        return rootView
    }

    private fun initView() {
        //setting all fields
        rootView.let {
            episode_name.text = episode.name
            episode_airdate.text = episode.airDate
            episode_code.text = episode.episode
            initRecyclerView()
        }
    }


    private fun initRecyclerView() {

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
