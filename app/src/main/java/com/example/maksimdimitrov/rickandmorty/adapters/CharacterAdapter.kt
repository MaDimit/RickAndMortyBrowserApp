package com.example.maksimdimitrov.rickandmorty.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.controller.ListFragment
import com.example.maksimdimitrov.rickandmorty.model.Model
import com.example.maksimdimitrov.rickandmorty.utilities.loadImage
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter(val dataSet : List<Model.Character>) : RecyclerView.Adapter<CharacterAdapter.CharacterVH>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterVH {
        context = parent.context
        if(context !is ListFragment.ItemClickListener){
           throw RuntimeException("$context must implement ItemClickListener")
        }
        val v = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false)
        return CharacterVH(v)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CharacterVH, position: Int) {
        val character = dataSet[position]
        holder.name.text = character.name
        holder.origin.text = character.origin.name
        holder.image.loadImage(character.image, context)
    }

    inner class CharacterVH(view : View) : RecyclerView.ViewHolder(view){
        val image = view.character_image
        val name = view.character_name
        val origin = view.character_origin

        init {
            view.setOnClickListener {
                (context as ListFragment.ItemClickListener).onListItemClick(dataSet[adapterPosition])
            }
        }
    }
}