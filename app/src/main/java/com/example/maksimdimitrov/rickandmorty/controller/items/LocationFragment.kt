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
import kotlinx.android.synthetic.main.fragment_location.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete

typealias Location = Model.Location

class LocationFragment : ItemFragment() {

    private val TAG = LocationFragment::class.qualifiedName
    private lateinit var charactersDataSet : List<Character>

    private lateinit var location: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getParcelable<Location>(LOCATION)?.let {
                location = it
                Log.d(TAG, "Location initialized: $location")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if(!this::charactersDataSet.isInitialized) {
            rootView = inflater.inflate(R.layout.fragment_location, container, false)
            initView()
        }
        return rootView
    }

    private fun initView() {
        //setting all fields
        rootView.apply {
            location_name.text = location.name
            location_type.text = location.type
            location_dimension.text = location.dimension
            initRecyclerView()
        }
    }


    private fun initRecyclerView() {
        val rv = rootView.location_residents_rv
        rv.layoutManager = LinearLayoutManager(context)
        rootView.progressBar.visibility = View.VISIBLE
        doAsync {
            charactersDataSet = location.residents
                    .map { BaseDataSource.getCharacter(it) }
                    .requireNoNulls()
                    .toList()
            onComplete {
                rv.adapter = CharacterAdapter(charactersDataSet)
                rootView.progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val LOCATION = "location"
        @JvmStatic
        fun newInstance(location: Location) =
                LocationFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(LOCATION, location)
                    }
                }
    }

}
