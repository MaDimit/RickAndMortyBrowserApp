package com.example.maksimdimitrov.rickandmorty.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.controller.ListFragment
import com.example.maksimdimitrov.rickandmorty.controller.items.Episode
import kotlinx.android.synthetic.main.item_episode.view.*

class EpisodeAdapter(val dataSet: List<Episode>) : RecyclerView.Adapter<EpisodeAdapter.EpisodeVH>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeVH {
        context = parent.context
        if (context !is ListFragment.ItemClickListener) {
            throw RuntimeException("$context must implement ItemClickListener")
        }
        val v = LayoutInflater.from(context).inflate(R.layout.item_episode, parent, false)
        return EpisodeVH(v)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: EpisodeVH, position: Int) {
        val episode = dataSet[position]
        holder.name.text = episode.name
        holder.airdate.text = episode.airDate
        holder.code.text = episode.episode
    }

    inner class EpisodeVH(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.item_episode_name
        val airdate = view.item_episode_airdate
        val code = view.item_episode_code

        init {
            view.setOnClickListener {
                (context as ListFragment.ItemClickListener).onListItemClick(dataSet[adapterPosition])
            }
        }
    }
}