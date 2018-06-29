package com.example.maksimdimitrov.rickandmorty.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.controller.ListFragment
import com.example.maksimdimitrov.rickandmorty.controller.items.Episode
import kotlinx.android.synthetic.main.item_character_episodes.view.*

class CharacterEpisodesAdapter(val dataSet: List<Episode>) : RecyclerView.Adapter<CharacterEpisodesAdapter.CharacterEpisdesVH>(){

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterEpisdesVH {
        context = parent.context
        if(context !is ListFragment.ItemClickListener){
            throw RuntimeException("$context must implement ItemClickListener")
        }
        val v = LayoutInflater.from(context).inflate(R.layout.item_character_episodes, parent, false)
        return CharacterEpisdesVH(v)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CharacterEpisdesVH, position: Int) {
        val episode = dataSet[position]
        holder.name.text = episode.name
        holder.code.text = episode.episode
    }

    inner class CharacterEpisdesVH(view : View) : RecyclerView.ViewHolder(view){
        val name = view.item_character_episode_name
        val code = view.item_character_episode_code

        init {
            view.setOnClickListener {
                (context as ListFragment.ItemClickListener).onListItemClick(dataSet[adapterPosition])
            }
        }
    }
}