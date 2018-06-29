package com.example.maksimdimitrov.rickandmorty.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.controller.ListFragment
import com.example.maksimdimitrov.rickandmorty.model.Model
import kotlinx.android.synthetic.main.item_location.view.*

class LocationAdapter(val dataSet: List<Model.Location>) : RecyclerView.Adapter<LocationAdapter.LocationVH>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationVH {
        context = parent.context
        if (context !is ListFragment.ItemClickListener) {
            throw RuntimeException("$context must implement ItemClickListener")
        }
        val v = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false)
        return LocationVH(v)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: LocationVH, position: Int) {
        val location = dataSet[position]
        holder.name.text = location.name
        holder.type.text = location.type
        holder.dimension.text = location.dimension
    }

    inner class LocationVH(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.item_location_name
        val type = view.item_location_type
        val dimension = view.item_location_dimension

        init {
            view.setOnClickListener {
                (context as ListFragment.ItemClickListener).onListItemClick(dataSet[adapterPosition])
            }
        }
    }
}