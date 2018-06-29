package com.example.maksimdimitrov.rickandmorty.controller.items


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.adapters.CharacterAdapter
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource
import com.example.maksimdimitrov.rickandmorty.model.Model
import kotlinx.android.synthetic.main.fragment_episode.*
import kotlinx.android.synthetic.main.fragment_episode.view.*
import kotlinx.android.synthetic.main.fragment_location.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import java.util.*

typealias Episode = Model.Episode

class EpisodeFragment : ItemFragment() {

    private val TAG = LocationFragment::class.qualifiedName

    private lateinit var episode: Episode
    private lateinit var charactersDataSet : List<Character>

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
        if(!this::charactersDataSet.isInitialized) {
            rootView = inflater.inflate(R.layout.fragment_episode, container, false)
            initView()
        }
        return rootView
    }

    private fun initView() {
        //setting all fields
        rootView.apply {
            episode_name.text = episode.name
            episode_airdate.text = episode.airDate
            episode_code.text = episode.episode
            initRecyclerView()
        }
    }


    private fun initRecyclerView() {
        val rv = rootView.episode_charachters_rv
        rv.layoutManager = LinearLayoutManager(context)
        rootView.episode_progressBar.visibility = View.VISIBLE
        doAsync {
            charactersDataSet = episode.characters
                    .map { BaseDataSource.getCharacter(it) }
                    .requireNoNulls()
                    .toList()
            onComplete {
                rv.adapter = CharacterAdapter(charactersDataSet)
                rootView.episode_progressBar.visibility = View.GONE
            }
        }
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
